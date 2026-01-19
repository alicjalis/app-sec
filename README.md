# app-sec

## deployment
docker compose --profile fullstack up --build

## save images
docker save -o appsec-images.tar $(docker compose config --images)

## load images
docker load -i appsec-images.tar
docker compose --profile fullstack up -d