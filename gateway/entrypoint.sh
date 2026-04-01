#!/bin/sh
set -e

# Wait for services to be ready before composition
echo "Waiting for services to be ready..."

wait_for_service() {
  local service_name=$1
  local url=$2
  echo "Checking $service_name at $url..."
  # Use wget or curl if available. Alpine usually has wget.
  until wget -q -T 1 -O- "$url" > /dev/null 2>&1 || curl -s -f "$url" > /dev/null 2>&1; do
    echo "Still waiting for $service_name..."
    sleep 2
  done
  echo "$service_name is UP!"
}

wait_for_service "User Service" "http://user:8081/v3/api-docs"
wait_for_service "Product Service" "http://product:8082/v3/api-docs"
wait_for_service "Ordering Service" "http://ordering:8083/v3/api-docs"

# Perform composition
echo "Composing supergraph from mesh.config.ts..."
mesh-compose -o /tmp/supergraph.graphql

# Serve the supergraph
echo "Starting hive-gateway..."
exec node /gateway/bin.mjs supergraph /tmp/supergraph.graphql
