# pull official base image
FROM node:16.19

# set working directory
WORKDIR /app
EXPOSE 3000

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY ./front/package.json ./
COPY ./front/package-lock.json ./
RUN npm install --silent
RUN npm install react-scripts@5.0.1 -g --silent

# add app
COPY ./front/. ./

# start app
CMD ["npm", "start"]