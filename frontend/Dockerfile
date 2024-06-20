# Use the official Node.js image with npm 7
FROM node:14

# Upgrade npm to version 7
RUN npm install -g npm@7

# Set the working directory
WORKDIR /app

# Copy the package.json and install dependencies
COPY package*.json ./
RUN npm install

# Copy the rest of the application code
COPY . .

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
CMD ["npm", "run", "serve"]
