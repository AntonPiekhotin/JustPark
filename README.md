# Parking project

## CI/CD

GitHub Actions is used for CI/CD. The workflow is defined in `.github/workflows/cicd.yml` file. There are 2 steps in the workflow:
- Building the project and running tests
- Creating a docker image and pushing it to the [Docker Hub](https://hub.docker.com/r/kartosha/justpark)

## Postman
Postman collection is available. You can import it to your Postman app by clicking the button below:  
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/29382454-5eef958a-9c95-4cd6-87a0-757afdad9347?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D29382454-5eef958a-9c95-4cd6-87a0-757afdad9347%26entityType%3Dcollection%26workspaceId%3Db6d90565-88d8-4914-a8f7-abd216f043af)