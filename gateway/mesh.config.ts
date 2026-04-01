import { defineConfig } from "@graphql-mesh/compose-cli";
import { loadOpenAPISubgraph } from "@omnigraph/openapi";

export const composeConfig = defineConfig({
  subgraphs: [
    {
      sourceHandler: loadOpenAPISubgraph("User", {
        source: "http://user:8081/v3/api-docs",
        endpoint: "http://user:8081",
      }),
    },
    {
      sourceHandler: loadOpenAPISubgraph("Product", {
        source: "http://product:8082/v3/api-docs",
        endpoint: "http://product:8082",
      }),
    },
    {
      sourceHandler: loadOpenAPISubgraph("Ordering", {
        source: "http://ordering:8083/v3/api-docs",
        endpoint: "http://ordering:8083",
      }),
    },
  ],
});
