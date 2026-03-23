# Offline Swagger UI (static HTML)

When each service exposes `springdoc-openapi`, generate `openapi.json` per service and bundle with Swagger UI:

```bash
docker run -p 8888:8080 -e SWAGGER_JSON=/spec/openapi.json -v "$PWD:/spec" swaggerapi/swagger-ui
```

For **offline** delivery:

1. Export JSON specs to `deliverables/swagger-ui/specs/`.
2. Run Swagger UI container locally, save page as HTML **or** use `swagger-ui-dist` npm package to build a static site.

Until OpenAPI is aggregated, use **`docs/architecture/api-reference.md`** and **Postman** as the API contract.
