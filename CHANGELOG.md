# Changelog
All notable changes to this project will be documented in this file.
The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/)
and adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.3.1]

### Fixed

- Ensure Builder.Default values are preserved for scheduled runs (they have null jobparams)

## [1.3.0]

### Changed

- Rest call now accepts properties to customize job execution (for example, export csv or json, along other configurable variables).

## [1.2.0]

### Changed

- Refactor to approach an hexagonal architecture

### Added

- Added rest call to execute job

## [1.1.0]

### Changed

- Refactor csv file creation to use same strategy than json file writer

### Added

- Added feature to create file in Json format.

## [1.0.0]

### Changed

- Refactor folder creation and writting to file to use software patterns

### Added

- Unit tests for 97% coverage

## [0.0.1]  

### Added

- Scafolding and initial commit
- Domain model and rowmapper

