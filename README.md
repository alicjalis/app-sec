# app-sec

## deplyment
1. change app-sec/frontend/src/environment/Environment.tsx
   
   \- export const REQUEST_PREFIX = "http://localhost:8010/proxy/";
   
   \+ export const REQUEST_PREFIX = "http://localhost:8080";
   
3. docker compose up --build


