#!/usr/bin/env bash

echo -n | openssl s_client -connect registry.stf.jus.br:443 -showcerts 2>/dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > stfca.pem

docker-machine scp stfca.pem default:/tmp/stfca.pem
docker-machine ssh default 'sudo mkdir -p /var/lib/boot2docker/certs'
docker-machine ssh default 'sudo mv /tmp/stfca.pem /var/lib/boot2docker/certs/stfca.pem'
echo "Reinicie a máquina com 'docker-machine restart default'"