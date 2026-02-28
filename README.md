# User Service

## This is a user service application

## This is a client for EurekaServer mcEurekaServer along with mcProductService

### Add dependency tree using following script everytime you add a dependency to pom.xml

mvn dependency:tree -DoutputFile=dependency.tree


## Configured Spring Cloud Vault for externalized configuration management
### To run vault server locally, use the following command
### vault is configured through docker compose file in the root directory of the project
### Start docker containers using the following command in the root directory of the project
### docker compose up -d
#### docker exec -it (container-id)b71b52e2a53d sh;    
#### / # export VAULT_ADDR='http://127.0.0.1:8200'
#### / # export VAULT_TOKEN=root
#### / #  vault status
#### / # vault kv get secret/user-service

##### Output of vault kv get secret/user-service command
shantanukumar@Shantanus-MacBook-Air ~ % docker exec -it b71b52e2a53d sh;     
/ # vault status
Error checking seal status: Get "https://127.0.0.1:8200/v1/sys/seal-status": http: server gave HTTP response to HTTPS client
/ # export VAULT_ADDR='http://127.0.0.1:8200'
/ # vault status
Key             Value
---             -----
Seal Type       shamir
Initialized     true
Sealed          false
Total Shares    1
Threshold       1
Version         1.13.3
Build Date      2023-06-06T18:12:37Z
Storage Type    inmem
Cluster Name    vault-cluster-e26f351e
Cluster ID      ab9fff1b-7ed2-3a12-065c-90fab8a994b8
HA Enabled      false
/ # export VAULT_TOKEN=root
/ # vault kv get secret/user-service
====== Secret Path ======
secret/data/user-service

======= Metadata =======
Key                Value
---                -----
created_time       2026-02-28T09:35:27.868910721Z
custom_metadata    <nil>
deletion_time      n/a
destroyed          false
version            1

=============== Data ===============
Key                           Value
---                           -----

<br/>
spring.datasource.password    Testing@123 <br/>
spring.datasource.username    root
