

One of the easiest ways to run a TigerGraph database is by using the [TigerGraph Docker image](https://docs.tigergraph.com/tigergraph-server/current/getting-started/docker).

You can use the following command to run the TigerGraph Docker image:
```bash
docker run -d -p 14022:22 -p 9000:9000 -p 14240:14240 --name tigergraph --ulimit nofile=1000000:1000000 -v ~/scripts:/home/tigergraph/scripts -t docker.tigergraph.com/tigergraph:latest
```
The way the image is designed (ATTOW) requires you to ssh into the container and start the services manually.
```bash
ssh -p 14022 tigergraph@localhost
gadmin start all
```
(The default password is `tigergraph`.)

> If you are running a Docker container, you can use the following command to run the TigerGraph Docker image:
>    ```
>    hostname -I (from bash)
>    ```
>    or 
>    ```
>    wsl hostname -I (from windows terminal)
>    ```