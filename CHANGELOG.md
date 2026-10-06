# Changelog for GottschCore Fabric 1.21.1

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.4.0] - 2026-10-06

Catches up with NeoForge GottschCore 2.7.0 for the classes other gottsch mods use.

### Added
- CommandResponseFormatter, FormatterConstants and ReportBuilder: shared chat formatting for mod commands.
- DimensionCoords: a Coords that also carries a dimension key and saves/loads it.
- WorldInfo: side checks, dimension and biome helpers, surface/height finding, block placement helpers.
- BlockContext, HalfBlock, IHalfBlock, FacingHalfBlock, WaterloggedFacingHalfBlock.
- bst interval trees (IntervalTree, CoordsIntervalTree and friends) with NBT save/load.
- ModUtil: resource location and registry name helpers.
- nbt.INBTSerializable: same method names as NeoForge's, for CoordsIntervalTree payloads.
- WeightedCollection.isEmpty() and remove(item); Coords.toVec3(); Box.equals()/hashCode().
- publishAll Gradle task: publishes to ../maven and mavenLocal together.

### Fixed
- FacingBlock now rotates its FACING property when a structure or template rotates it; it kept its original facing before.

## [2.3.0] - 2024-10-27

### Changed
- [x]deprecated usage of Coords constructors in favor of static of() methods.
- [x]deprecated Quantity

### Added
- [x]InterRange
- [x]DoubleRange

## [2.2.0] - 2024-08-20

### Changed
- changed WeightedCollection.add() to synchronized. 

### Added
- WeightedCollection.remove(T key) method.
