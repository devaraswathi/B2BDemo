#!/bin/bash

# Execute as: $ sh installArchetypeLocally.sh

# Installs a new project seed archetype.
# After installing archetype, change to root folder for your new projects and execute:
#
# $ mvn archetype:generate -DarchetypeGroupId=com.kroger.scrs -DarchetypeArtifactId=scrs-project-seed-archetype -DgroupId=com.kroger.scrs -DartifactId=demo -DinteractiveMode=false

# Create the archetype project based on this seed project. If needed, files that should be
# excluded (i.e. only needed by the archetype and not the new project scaffolding) can be
# filtered in the archetype.properties file.
mvn clean archetype:create-from-project -Darchetype.properties=archetype.properties

# Need to add .gitignore to archetype artifacts manually.
cp .gitignore target/generated-sources/archetype/src/main/resources/archetype-resources/

# Update the generated archetype POM to include packaging .gitignore into resultant jar file.
sed -i.bak 's#</pluginManagement>#</pluginManagement><plugins><plugin><artifactId>maven-resources-plugin</artifactId><version>3.1.0</version><configuration><addDefaultExcludes>false</addDefaultExcludes></configuration></plugin></plugins>#' target/generated-sources/archetype/pom.xml

# Update the generated archetype POM to include encoding to suppress output warnings.
sed -i.bak 's#<build>#<properties><project.build.sourceEncoding>UTF-8</project.build.sourceEncoding></properties><build>#' target/generated-sources/archetype/pom.xml

# Update the generated archetype POM to include distributionManagement repository of azure artifacts.
sed -i.bak 's#</project>#<distributionManagement><repository><id>scrsregistry</id><url>https://pkgs.dev.azure.com/KrogerTechnology/SCRS/_packaging/scrsregistry/maven/v1</url><releases><enabled>true</enabled></releases><snapshots><enabled>true</enabled></snapshots></repository></distributionManagement></project>#' target/generated-sources/archetype/pom.xml

# Build the generated archetype and install in local registry (~/.m2 folder)
mvn -f target/generated-sources/archetype/pom.xml clean install
