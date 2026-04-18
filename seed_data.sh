#!/bin/bash

# Base URL for the services (assuming Traefik is running on localhost:80)
BASE_URL="http://localhost"

# Step 1: Creating Users
echo "Creating Users..."
USER_ID_1=$(curl -s -X POST "${BASE_URL}/user/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "Password123!"
  }' | jq -r '.id')
echo "Created User 1 with ID: $USER_ID_1"

USER_ID_2=$(curl -s -X POST "${BASE_URL}/user/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "password": "Password123!"
  }' | jq -r '.id')
echo "Created User 2 with ID: $USER_ID_2"

# Step 2: Creating 5 Products
echo -e "\nCreating 5 Products..."
declare -a PRODUCT_IDS
PRODUCT_NAMES=("Mechanical Keyboard" "Gaming Mouse" "USB-C Hub" "Monitor" "Webcam")
PRODUCT_PRICES=(89.99 45.50 29.99 199.00 75.00)

for i in {0..4}; do
    PID=$(curl -s -X POST "${BASE_URL}/product/api/products" \
      -H "Content-Type: application/json" \
      -d "{
        \"name\": \"${PRODUCT_NAMES[$i]}\",
        \"description\": \"High quality ${PRODUCT_NAMES[$i]}\",
        \"price\": ${PRODUCT_PRICES[$i]},
        \"stock\": 50,
        \"available\": true
      }" | jq -r '.id')
    PRODUCT_IDS+=($PID)
    echo "Created Product: ${PRODUCT_NAMES[$i]} (ID: $PID)"
done

# Step 3: Placing 3 Orders
echo -e "\nPlacing 3 Orders..."

# Order 1: User 1 buys Keyboard and Mouse
curl -s -X POST "${BASE_URL}/ordering/api/carts/user/$USER_ID_1/items" \
     -H "Content-Type: application/json" \
     -d "{\"productId\": \"${PRODUCT_IDS[0]}\", \"quantity\": 1}" > /dev/null
curl -s -X POST "${BASE_URL}/ordering/api/carts/user/$USER_ID_1/items" \
     -H "Content-Type: application/json" \
     -d "{\"productId\": \"${PRODUCT_IDS[1]}\", \"quantity\": 1}" > /dev/null
ORDER_ID_1=$(curl -s -X POST "${BASE_URL}/ordering/api/orders/user/$USER_ID_1" | jq -r '.id')
echo "User 1 placed Order (ID: $ORDER_ID_1)"

# Order 2: User 2 buys Monitor
curl -s -X POST "${BASE_URL}/ordering/api/carts/user/$USER_ID_2/items" \
     -H "Content-Type: application/json" \
     -d "{\"productId\": \"${PRODUCT_IDS[3]}\", \"quantity\": 1}" > /dev/null
ORDER_ID_2=$(curl -s -X POST "${BASE_URL}/ordering/api/orders/user/$USER_ID_2" | jq -r '.id')
echo "User 2 placed Order (ID: $ORDER_ID_2)"

# Order 3: User 1 buys Webcam
curl -s -X POST "${BASE_URL}/ordering/api/carts/user/$USER_ID_1/items" \
     -H "Content-Type: application/json" \
     -d "{\"productId\": \"${PRODUCT_IDS[4]}\", \"quantity\": 1}" > /dev/null
ORDER_ID_3=$(curl -s -X POST "${BASE_URL}/ordering/api/orders/user/$USER_ID_1" | jq -r '.id')
echo "User 1 placed another Order (ID: $ORDER_ID_3)"

echo -e "\nSeeding Completed Successfully!"
