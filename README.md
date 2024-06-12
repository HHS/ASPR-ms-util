[![GPL LICENSE][license-shield]][license-url]
[![GitHub tag (with filter)][tag-shield]][tag-url]
[![GitHub contributors][contributors-shield]][contributors-url]
[![GitHub Workflow Status (with event)][dev-build-shield]][dev-build-url]
[![GitHub Workflow Status (with event)][build-shield]][build-url]

# Modeling Utilities
This project contains tools and utilities that are useful to use with simulation models.  
More Specifically, these utilities are used within [GCM](https://github.com/HHS/ASPR-8) and thus this project is required to be cloned and built in order to use GCM.

As of v4.1.1, this project is in Maven Central.

## License
Distributed under the GPLv3 License. See [LICENSE](LICENSE) for more information.

Please read the [HHS vulnerability discloure](https://www.hhs.gov/vulnerability-disclosure-policy/index.html).

## Usage
To use this project in your project, simply add the following dependency to your `dependencies` section of your pom.xml file.
```
<dependency>
    <groupId>gov.hhs.aspr.ms</groupId>
    <artifactId>util</artifactId>
    <version>4.2.0</version>
</dependency>
```
## Building from Source

### Requirements
- Maven 3.8.6
- Java 17
- Your favorite IDE for use with Java Projects

### Building
To build this project:
- Clone the repo
- open a command line terminal
- navigate to the root folder of this project
- run the command: `mvn clean install`

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/HHS/ASPR-ms-util
[contributors-url]: https://github.com/HHS/ASPR-ms-util/graphs/contributors
[tag-shield]: https://img.shields.io/github/v/tag/HHS/ASPR-ms-util
[tag-url]: https://github.com/HHS/ASPR-8/releases/tag/latest
[license-shield]: https://img.shields.io/github/license/HHS/ASPR-ms-util
[license-url]: LICENSE
[dev-build-shield]: https://img.shields.io/github/actions/workflow/status/HHS/ASPR-ms-util/dev_build.yml?label=dev-build
[dev-build-url]: https://github.com/HHS/ASPR-ms-util/actions/workflows/dev_build.yml
[build-shield]: https://img.shields.io/github/actions/workflow/status/HHS/ASPR-ms-util/release_build.yml?label=release-build
[build-url]: https://github.com/HHS/ASPR-ms-util/actions/workflows/release_build.yml.yml
