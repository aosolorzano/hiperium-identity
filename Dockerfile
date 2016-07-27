# Based on Ubuntu image
FROM jboss/wildfly
MAINTAINER "Andres Solorzano" andres.solorzano@hiperium.com

# Set the JBOSS_HOME env variable
ENV JBOSS_HOME /opt/jboss/wildfly

# Copy hiperium service files into Wildfly
ADD opt/postgresql-9.4-module.tar.gz $JBOSS_HOME/modules/system/layers/base/org/
COPY opt/standalone-ha.xml $JBOSS_HOME/standalone/configuration/
COPY target/hiperium-identity.war $JBOSS_HOME/standalone/deployments/

# Creates the wildlfy user administrator
RUN $JBOSS_HOME/bin/add-user.sh admin admin123 --silent

# Expose the ports we're interested in
EXPOSE 8080 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-ha.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-Djboss.node.name=identity-node1"]
