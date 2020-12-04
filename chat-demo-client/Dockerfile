### STAGE 1: Build ###
FROM node:14.1-alpine AS builder
WORKDIR /opt/web
COPY package.json package-lock.json ./
RUN npm install
ENV PATH="./node_modules/.bin:$PATH"
COPY . ./
RUN ng build --prod

### STAGE 2: Run ###
FROM nginx:1.17-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /opt/web/dist/chat-demo-client /usr/share/nginx/html