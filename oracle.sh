#!/bin/bash
#
# restart_oracle.sh - Clean restart of Oracle instance
#
# Usage: ./restart_oracle.sh
#
# Assumes:
#   - ORACLE_HOME is set in the current environment
#   - Docker container running Oracle is named "oracle"
#   - Oracle SID is EE
#

set -u  # error on unset variables

# --- 1. Kill any lingering Oracle background processes (ora_*) ---
echo "[*] Killing lingering Oracle processes..."
ps -ef | grep ora_ | grep EE | awk '{print $2}' | xargs -r kill -9

# --- 2. Remove lock and SGA definition files ---
if [ -z "${ORACLE_HOME:-}" ]; then
    echo "[!] ORACLE_HOME is not set. Aborting."
    exit 1
fi

echo "[*] Removing lock and SGA files from \$ORACLE_HOME/dbs..."
rm -f "$ORACLE_HOME/dbs/lkEE"
rm -f "$ORACLE_HOME/dbs/sgadefEE.dbf"

# --- 3. Inside the docker container, run SHUTDOWN ABORT + STARTUP ---
echo "[*] Running SHUTDOWN ABORT and STARTUP inside the oracle container..."

docker exec -i oracle bash -lc '
    su - oracle -c "
        sqlplus -s / as sysdba <<EOF
SHUTDOWN ABORT
STARTUP
EXIT
EOF
    "
'

echo "[*] Done."