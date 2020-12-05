FROM nginx:1.17-alpine
WORKDIR /opt/web
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY /dist/chat-demo-client /usr/share/nginx/html