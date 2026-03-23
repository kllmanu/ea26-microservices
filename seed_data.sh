#!/bin/bash

# Base URL for the services (assuming Traefik is running on localhost:80)
BASE_URL="http://localhost"

echo "Step 1: Creating a User..."
USER_RESPONSE=$(curl -s -X POST "${BASE_URL}/user/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Wonderland",
    "email": "alice@example.com",
    "password": "Password123!"
  }')

USER_ID=$(echo $USER_RESPONSE | jq -r '.id')
if [ "$USER_ID" == "null" ] || [ -z "$USER_ID" ]; then
    echo "Failed to create user. Response: $USER_RESPONSE"
    exit 1
fi
echo "Created User with ID: $USER_ID"

echo -e "\nStep 2: Creating a Product..."
PRODUCT_RESPONSE=$(curl -s -X POST "${BASE_URL}/product/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Mechanical Keyboard",
    "description": "RGB Mechanical Keyboard with Blue Switches",
    "price": 89.99,
    "stock": 50,
    "available": true
  }')

PRODUCT_ID=$(echo $PRODUCT_RESPONSE | jq -r '.id')
if [ "$PRODUCT_ID" == "null" ] || [ -z "$PRODUCT_ID" ]; then
    echo "Failed to create product. Response: $PRODUCT_RESPONSE"
    exit 1
fi
echo "Created Product with ID: $PRODUCT_ID"

echo -e "\nStep 3: Adding Product to User's Cart..."
CART_RESPONSE=$(curl -s -X POST "${BASE_URL}/ordering/api/carts/user/${USER_ID}/items" \
  -H "Content-Type: application/json" \
  -d "{
    \"productId\": \"$PRODUCT_ID\",
    \"quantity\": 2
  }")

echo "Cart Updated: $(echo $CART_RESPONSE | jq -c '.')"

echo -e "\nStep 4: Placing an Order..."
ORDER_RESPONSE=$(curl -s -X POST "${BASE_URL}/ordering/api/orders/user/${USER_ID}")

ORDER_ID=$(echo $ORDER_RESPONSE | jq -r '.id')
if [ "$ORDER_ID" == "null" ] || [ -z "$ORDER_ID" ]; then
    echo "Failed to place order. Response: $ORDER_RESPONSE"
    exit 1
fi
echo "Successfully placed Order with ID: $ORDER_ID"
echo "Order Details: $(echo $ORDER_RESPONSE | jq -c '.')"

echo -e "\nSeeding Completed Successfully!"
