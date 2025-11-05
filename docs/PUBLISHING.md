# Publishing FluentRules to Maven Central

This guide walks maintainers through preparing, verifying, and releasing a new
FluentRules version to the Maven Central Repository. All steps assume you have
push access to the project repository and the Sonatype OSSRH staging profile
assigned to the `com.fluentrules` group identifier.

## 1. Prerequisites

1. **Java & Maven**
   - Install Java 17 or newer and Apache Maven 3.9 or newer.
   - Verify installation:
     ```bash
     java -version
     mvn -version
     ```

2. **GPG key**
   - Generate (or reuse) an OpenPGP key pair used to sign the artifacts:
     ```bash
     gpg --full-generate-key
     gpg --list-secret-keys --keyid-format=long
     ```
   - Export the **public** key to a key server accepted by Maven Central
     (for example https://keys.openpgp.org):
     ```bash
     gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>
     ```

3. **Sonatype OSSRH account**
   - Register at https://issues.sonatype.org and request access for the
     `com.fluentrules` namespace if you have not already done so.

4. **Maven settings**
   - Store your Sonatype credentials and GPG information in `~/.m2/settings.xml`:
     ```xml
     <settings>
       <servers>
         <server>
           <id>ossrh</id>
           <username>${env.OSSRH_USERNAME}</username>
           <password>${env.OSSRH_PASSWORD}</password>
         </server>
       </servers>
       <profiles>
         <profile>
           <id>gpg</id>
           <properties>
             <gpg.executable>gpg</gpg.executable>
             <gpg.keyname><KEY_ID></gpg.keyname>
             <gpg.passphrase>${env.GPG_PASSPHRASE}</gpg.passphrase>
           </properties>
         </profile>
       </profiles>
       <activeProfiles>
         <activeProfile>gpg</activeProfile>
       </activeProfiles>
     </settings>
     ```

## 2. Prepare the Release

1. **Update project metadata**
   - Verify `pom.xml` contains the desired release version, description,
     license, developer and SCM sections (see "Project metadata checklist"
     below).
   - Update `README.md` and `CHANGELOG.md` with release highlights.

2. **Ensure a clean workspace**
   ```bash
   git status
   ```

3. **Run the full test matrix**
   - First execute the lightweight offline test harness (useful when working in
     restricted environments):
     ```bash
     ./scripts/run-tests.sh
     ```
   - Then execute the official Maven build to confirm compatibility with the
     public toolchain (requires network access):
     ```bash
     mvn -B -Prelease clean verify
     ```

4. **Check code coverage (optional but recommended)**
   - Integrate tools such as JaCoCo if a coverage gate is required.

## 3. Update the Version

1. Set the release version:
   ```bash
   mvn versions:set -DnewVersion=0.1.0
   mvn versions:commit
   ```
2. Commit the version bump and tag the release:
   ```bash
   git commit -am "chore(release): v0.1.0"
   git tag v0.1.0
   ```

## 4. Deploy to OSSRH

1. Deploy signed artifacts to the OSSRH staging repository:
   ```bash
   mvn -B -Prelease clean deploy
   ```
2. Log into https://oss.sonatype.org, review the staging repository, and close
   it. Resolve any reported validation issues.
3. When the staging repository is ready, release it from the OSSRH UI. Maven
   Central sync typically completes within minutes.

## 5. Post-release Steps

1. Bump the project back to the next snapshot version:
   ```bash
   mvn versions:set -DnewVersion=0.2.0-SNAPSHOT
   mvn versions:commit
   git commit -am "chore: prepare for next development iteration"
   ```
2. Push the release tag and commits:
   ```bash
   git push origin main --tags
   ```
3. Publish release notes summarising the changes.

## Project Metadata Checklist

Before deploying to Maven Central ensure the `pom.xml` includes the following
sections as required by the repository guidelines:

- `<name>`, `<description>`, and `<url>` describing the project.
- `<licenses>` with SPDX-compatible license information.
- `<developers>` listing at least one maintainer.
- `<scm>` section pointing to the Git repository.
- `<distributionManagement>` entries referencing OSSRH endpoints for releases
  and snapshots.
- `<issueManagement>` and `<ciManagement>` entries if available.

Refer to the official OSSRH guide for more background:
https://central.sonatype.org/publish/publish-guide/
