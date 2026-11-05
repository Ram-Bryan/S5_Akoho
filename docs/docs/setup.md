## Setup DB:

Creer le conteneur docker oracle. Run le conteneur:

docker run -d --name oracle --restart unless-stopped --shm-size=1g -p 1521:1521 obsmartsoft/orcl.db.11g:latest

sudo systemctl enable docker

Ensuite:

docker exec -it oracle bash
su - oracle
export ORACLE_HOME=/u01/app/oracle-product/11.2.0/EE
export ORACLE_SID=EE
export PATH=$ORACLE_HOME/bin:$PATH
sqlplus / as sysdba

```sql

STARTUP
CREATE USER akoho IDENTIFIED BY Akoho123;
ALTER USER akoho QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION, CREATE TABLE, CREATE VIEW, CREATE SEQUENCE, CREATE PROCEDURE, IMP_FULL_DATABASE TO akoho;
CREATE OR REPLACE DIRECTORY dump_dir AS '/u01/app/oracle/dumps';
GRANT READ, WRITE ON DIRECTORY dump_dir TO akoho;
EXIT;

```
si startup fait erreur, EXIT sur un terminal normal:
ps -ef | grep ora_ | grep EE | awk '{print $2}' | xargs -r kill -9
rm -f $ORACLE_HOME/dbs/lkEE
rm -f $ORACLE_HOME/dbs/sgadefEE.dbf
revenir en tant que sysdba et fare SHUTDOWN ABORT puis STARTUP et continuer sur les commandes en haut



mkdir -p /u01/app/oracle/dumps
exit
exit

Sur un terminal normal:
docker cp /path/to/your/file.dmp oracle:/u01/app/oracle/dumps/

docker cp /home/bryan/ITU/S5/Prog/Reprise_code/Akoho/arbiochemstandard_20261002_160451.dmp oracle:/u01/app/oracle/dumps/

Revenir dans docker:
docker exec -it oracle bash
su - oracle
export ORACLE_HOME=/u01/app/oracle-product/11.2.0/EE
export ORACLE_SID=EE
export PATH=$ORACLE_HOME/bin:$PATH
imp akoho/Akoho123 FILE=/u01/app/oracle/dumps/arbiochemstandard_20261002_160451.dmp FROMUSER=arbiochemstandard TOUSER=akoho LOG=import.log


Changer la connection dans src/java/
./arbiochem-ejb/src/java/apj.properties

En:
apj.connection.url=jdbc:oracle:thin:@localhost:1521:EE
apj.connection.user=akoho
apj.connection.password=Akoho123
apj.debug=true


sudo apt install ant
Aller dans le root du projet et faire:
ant -f build-tomcat.xml -Dtomcat.dir=/absolute/path/to/tomcat-9


## Faire les env de oracle permanet:
```bash
cat >> ~/.bash_profile << 'EOF'
export ORACLE_HOME=/u01/app/oracle-product/11.2.0/EE
export ORACLE_SID=EE
export PATH=$ORACLE_HOME/bin:$PATH
EOF
```
Immediatement:
```bash
source ~/.bash_profile
```


## Si tomcat Activer tomcat
Run tomcat sur port 8088:
cd /home/bryan/Softwares/Tomcat/tomcat-9
cp conf/server.xml conf/server.xml.bak
sed -i 's/<Connector port="8080"/<Connector port="8088"/' conf/server.xml
grep -n 'Connector port' conf/server.xml

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
/home/bryan/Softwares/Tomcat/tomcat-9/bin/catalina.sh run


## Si wildfly
export WILDFLY_HOME=/home/bryan/Softwares/Wildfly/wildfly-10
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
$WILDFLY_HOME/bin/standalone.sh -Djboss.http.port=8088

Aller sur:
http://localhost:8088/async-standard/

Ensuite on se log avec: 
Identifiant: admin
Mot de passe: test


Entrer dans un user oracle:
docker exec -it <container_name> bash
su - oracle
sqlplus akoho/Akoho123


(si pas encore fait, il faut export les env, si pas encore fait la commande de bashrc)
