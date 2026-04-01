FROM ghcr.io/graphql-hive/gateway:latest

# Switch to root
USER root

# Create a fresh directory for our installation to avoid "idealTree" conflicts in /app
WORKDIR /gateway-build

# Initialize and install dependencies
RUN npm init -y && \
    npm install @omnigraph/openapi @graphql-mesh/transport-rest @graphql-mesh/compose-cli

# Copy the entrypoint script and wait-for-services script
COPY entrypoint.sh /gateway-build/entrypoint.sh
COPY wait-for-services.js /gateway-build/wait-for-services.js
RUN chmod +x /gateway-build/entrypoint.sh

# Add local node_modules/.bin to PATH
ENV PATH="/gateway-build/node_modules/.bin:${PATH}"

# Add our node_modules to NODE_PATH so hive-gateway can find transports/plugins
# The base image already has /gateway/node_modules if it exists
ENV NODE_PATH="/gateway-build/node_modules:/gateway/node_modules:${NODE_PATH}"

# The entrypoint script will handle composition and serving
# We'll run from this directory to ensure modules are found
WORKDIR /gateway-build
ENTRYPOINT ["/gateway-build/entrypoint.sh"]
