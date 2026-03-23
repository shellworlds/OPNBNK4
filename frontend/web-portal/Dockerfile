FROM node:20-alpine
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .

ENV BROWSER=none
ENV CHOKIDAR_USEPOLLING=true
ENV WATCHPACK_POLLING=true
ENV REACT_APP_GATEWAY_URL=http://localhost:8080

EXPOSE 3000

CMD ["npm", "start"]
