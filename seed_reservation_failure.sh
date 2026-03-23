#!/bin/bash

# Base URL for the services (assuming Traefik is running on localhost:80)
BASE_URL="http://localhost"

echo "Step 1: Creating a User for failure test..."
USER_RESPONSE=$(curl -s -X POST "${BASE_URL}/user/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Builder",
    "email": "bob@example.com",
    "password": "Password123!"
  }')

USER_ID=$(echo $USER_RESPONSE | jq -r '.id')
if [ "$USER_ID" == "null" ] || [ -z "$USER_ID" ]; then
    echo "Failed to create user. Response: $USER_RESPONSE"
    exit 1
fi
echo "Created User with ID: $USER_ID"

echo -e "\nStep 2: Creating a Product with limited stock (5 units)..."
PRODUCT_RESPONSE=$(curl -s -X POST "${BASE_URL}/product/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Limited Edition Widget",
    "description": "Only 5 in stock",
    "price": 99.99,
    "stock": 5,
    "available": true
  }')

PRODUCT_ID=$(echo $PRODUCT_RESPONSE | jq -r '.id')
if [ "$PRODUCT_ID" == "null" ] || [ -z "$PRODUCT_ID" ]; then
    echo "Failed to create product. Response: $PRODUCT_RESPONSE"
    exit 1
fi
echo "Created Product with ID: $PRODUCT_ID"

echo -e "\nStep 3: Adding 10 units of the product to the user's cart (more than available stock)..."
# Note: The Cart API currently doesn't check stock synchronously during "add to cart", 
# which is perfect for testing the asynchronous saga failure.
CART_RESPONSE=$(curl -s -X POST "${BASE_URL}/ordering/api/carts/user/${USER_ID}/items" \
  -H "Content-Type: application/json" \
  -d "{
    \"productId\": \"$PRODUCT_ID\",
    \"quantity\": 10
  }")

echo "Cart Updated: $(echo $CART_RESPONSE | jq -c '.')"

echo -e "\nStep 4: Placing the Order (Initial status should be PENDING)..."
ORDER_RESPONSE=$(curl -s -X POST "${BASE_URL}/ordering/api/orders/user/${USER_ID}")

ORDER_ID=$(echo $ORDER_RESPONSE | jq -r '.id')
if [ "$ORDER_ID" == "null" ] || [ -z "$ORDER_ID" ]; then
    echo "Failed to place order. Response: $ORDER_RESPONSE"
    exit 1
fi
echo "Successfully placed Order with ID: $ORDER_ID"
echo "Initial Order Status: $(echo $ORDER_RESPONSE | jq -r '.status')"

echo -e "\nStep 5: Waiting 5 seconds for the Saga to process the failure..."
sleep 5

echo -e "\nStep 6: Checking Final Order Status (Should be CANCELLED)..."
FINAL_ORDER_RESPONSE=$(curl -s -X GET "${BASE_URL}/ordering/api/orders/${ORDER_ID}")
FINAL_STATUS=$(echo $FINAL_ORDER_RESPONSE | jq -r '.status')

echo "Final Order Status: $FINAL_STATUS"

if [ "$FINAL_STATUS" == "CANCELLED" ]; then
    echo -e "\nSuccess! The saga correctly cancelled the order due to insufficient stock."
else
    echo -e "\nFailure! The order status is $FINAL_STATUS, but expected CANCELLED."
    exit 1
fi

echo -e "\nSeeding and Saga Failure Test Completed Successfully!"
