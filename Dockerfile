FROM openjdk:17-alpine
ENV directory /app/
WORKDIR ${directory}
ADD build/distributions/website-backend-shadow-*.zip .
RUN unzip -q -o *.zip
RUN rm -fv *.zip
RUN mv website-backend-shadow*/* .
RUN rm -rf website-backend-shadow*/
WORKDIR bin
EXPOSE 8081:8081
ENTRYPOINT ./website-backend