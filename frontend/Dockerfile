# Build stage
FROM node:14 as build-stage

# Set the working directory
WORKDIR /app

# Copy the package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Production stage
FROM nginx:stable-alpine

# Copy the built files from the build stage
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Expose the port the application runs on
EXPOSE 80

# Command to run the application
CMD ["nginx", "-g", "daemon off;"]
