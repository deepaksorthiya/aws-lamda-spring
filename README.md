# aws-lamda-spring serverless API

The aws-lamda-spring project, created with [
`aws-serverless-java-container`](https://github.com/aws/serverless-java-container).

The starter project defines a simple `/ping` resource that can accept `GET` requests with its tests.

The project folder also includes a `template.yml` file. You can use
this [SAM](https://github.com/awslabs/serverless-application-model) file to deploy the project to AWS Lambda and Amazon
API Gateway or test in local with the [SAM CLI](https://github.com/awslabs/aws-sam-cli).

## Pre-requisites

* [AWS CLI](https://aws.amazon.com/cli/)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [Gradle](https://gradle.org/) or [Maven](https://maven.apache.org/)

## Building the project

You can use the SAM CLI to quickly build the project

```bash
sam build
```

OR

```bash
samlocal build
```

## Testing locally with the SAM CLI

From the project root folder - where the `template.yml` file is located - start the API with the SAM CLI.

```bash
$ sam local start-api

...
Mounting com.amazonaws.serverless.archetypes.StreamLambdaHandler::handleRequest (java21) at http://127.0.0.1:3000/{proxy+} [OPTIONS GET HEAD POST PUT DELETE PATCH]
...
```

Using a new shell, you can send a test ping request to your API:

```bash
$ curl -s http://127.0.0.1:3000/ping | jq

{
    "pong": "Hello, World!"
}
``` 

```bash
curl -d '{"key1":"value1", "key2":"value2"}' -H "Content-Type: application/json" -X POST http://localhost:3000/foo/male/bar/25?name=Ricky
```

```bash
curl -d '{"key1":"value1", "key2":"value2"}' -H "Content-Type: application/json" -X POST http://eh1sooyylm.execute-api.localhost.localstack.cloud:4566/dev/foo/male/bar/25?name=Ricky
```

## Debug Locally

```bash
# Invoke a function locally in debug mode on port 5005
sam local invoke -d 5005 <function logical id>
OR
# Start local API Gateway in debug mode on port 5005
sam local start-api -d 5005
```

Fire first request ``http://127.0.0.1:3000/`` to up docker container.

Setup Remote Debug In your IDE and attached port ``5005`` and host ``localhost``

Set Breakpoint and Hit ``http://127.0.0.1:3000/ping``

Test Using event

```bash
sam local invoke --event events/event.json
```

```bash
sam local invoke --event events/event.json -d 5005
```

## Deploying to AWS

To deploy the application in your AWS account, you can use the SAM CLI's guided deployment process and follow the
instructions on the screen

```
$ sam deploy --guided
```

Once the deployment is completed, the SAM CLI will print out the stack's outputs, including the new application URL. You
can use `curl` or a web browser to make a call to the URL

```
...
-------------------------------------------------------------------------------------------------------------
OutputKey-Description                        OutputValue
-------------------------------------------------------------------------------------------------------------
AwsLamdaSpringApi - URL for application            https://xxxxxxxxxx.execute-api.us-west-2.amazonaws.com/Prod/pets
-------------------------------------------------------------------------------------------------------------
```

Copy the `OutputValue` into a browser or use curl to test your first request:

For LocalStack Testing

```bash
$ curl -s https://xxxxxxx.execute-api.localhost.localstack.cloud/dev/ping | jq

{
    "pong": "Hello, World!"
}
```
