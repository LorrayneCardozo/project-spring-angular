FROM node:18.10.0

WORKDIR /app
RUN npm install -g @angular/cli@16.2.0
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 4200

CMD ["ng", "serve", "--configuration", "production", "--host", "0.0.0.0"]