FROM node:20-alpine AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci
COPY . .
ARG EXPO_PUBLIC_GATEWAY_URL=http://localhost:8080
ENV EXPO_PUBLIC_GATEWAY_URL=$EXPO_PUBLIC_GATEWAY_URL
RUN npx expo export --platform web

FROM nginx:1.27-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 3010
CMD ["nginx", "-g", "daemon off;"]
