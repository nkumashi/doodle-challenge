#FROM nginx:1.17.10
#WORKDIR /usr/share/nginx/html
#COPY ./dist/chat-demo-client/ ./
#EXPOSE 80


### STAGE 1: Build ###
FROM node:12.7-alpine AS build
RUN mkdir -p /app
WORKDIR /app
COPY package.json package-lock.json /app
RUN npm install
COPY . /app
RUN npm run build --prod

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY --from=build /app/dist/chat-demo-client /usr/share/nginx/html
EXPOSE 80
