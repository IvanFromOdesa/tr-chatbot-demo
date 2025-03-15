This is the ```UI``` component of the application. It is built with ```React v.19.0.0``` and ```TypeScript ~5.7.2```.

The ```UI``` is developed independently of the Spring Boot server component,
and it uses the ```proxy``` ```Node.js express``` server for mock data, required in ```dev``` mode.

To start in the ```dev``` mode via ```vite``` web-server (you'll also need to start the ```proxy``` server):

- `dev`

To build the application:

- `build`

The built app (at ```/dist```) is to be placed under ```src/main/resources/static/public/bundles```.
Remember to check the filename hashes referred to by ```html templates``` under ```src/main/resources/templates```.

You can find the dependencies at ```package.json```.