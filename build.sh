#!/usr/bin/env bash
set -e

# ---- Defaults (override by exporting before running the script) ----
 export WILDFLY_HOME="${WILDFLY_HOME:-/home/bryan/Softwares/Wildfly/wildfly-10}"
# export WILDFLY_HOME="${WILDFLY_HOME:-/home/itu/Softwares/wildfly/wildfly-10}"
export JAVA_HOME="${JAVA_HOME:-/usr/lib/jvm/java-8-openjdk-amd64}"

# Refuse to build with the wrong JDK
if [ ! -x "$JAVA_HOME/bin/javac" ]; then
    echo "ERROR: javac not found in $JAVA_HOME" >&2
    exit 1
fi

JAVAC_VERSION=$("$JAVA_HOME/bin/javac" -version 2>&1)
case "$JAVAC_VERSION" in
    1.8.*|javac\ 1.8.*) ;;
    *) echo "WARNING: expected JDK 8, got: $JAVAC_VERSION" >&2 ;;
esac

export PATH="$JAVA_HOME/bin:$PATH"

# Default deploy.dir if not provided
DEPLOY_DIR="${1:-$WILDFLY_HOME/standalone/deployments}"

exec ant -f build.xml -Ddeploy.dir="$DEPLOY_DIR"