### STAGE 1: Build ###
FROM node:12.7-alpine AS build
RUN mkdir -p /app
WORKDIR /app
COPY . /app
RUN npm install
RUN npm run build --prod

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
ADD nginx-default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
#EXPOSE 80
