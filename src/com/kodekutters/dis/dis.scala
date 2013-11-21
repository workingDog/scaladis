/*
 * Copyright (c) 2013, Ringo Wathelet
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this
 *   list of conditions and the following disclaimer in the documentation and/or
 *   other materials provided with the distribution.
 *
 * - Neither the name of "scaladis" nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.kodekutters.dis

import javax.xml.bind.DatatypeConverter

/**
 * @author Ringo Wathelet
 *         Date: 21/11/2013
 *         Version: 1
 *
 */

//------------------------------------------------------------------------------------------------
//----------------helper--------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------
class Base64Binary(_vector: Vector[Byte]) extends scala.collection.IndexedSeq[Byte] {
  val vector = _vector
  def length = vector.length
  def apply(idx: Int): Byte = vector(idx)
  override def toString: String = DatatypeConverter.printBase64Binary(vector.toArray)
}

object Base64Binary {
  def apply(xs: Byte*): Base64Binary = {
    import scala.collection.breakOut
    val vector: Vector[Byte] = (xs.toIndexedSeq map {x: Byte => x})(breakOut)
    new Base64Binary(vector)
  }

  def apply(value: String): Base64Binary = {
    val array = DatatypeConverter.parseBase64Binary(value)
    apply(array: _*)
  }

  def unapplySeq[Byte](x: Base64Binary) = Some(x.vector)
}

//------------------------------------------------------------------------------------------------
//------------DIS7--------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------

case class AcknowledgePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  acknowledgeFlag: Int,
  requestID: Long,
  responseFlag: Int) extends SimulationManagementFamilyTrait


trait SimulationManagementFamilyTrait extends PduTrait {
  val originatingEntityID: Option[EntityID]
  val receivingEntityID: Option[EntityID]
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class SimulationManagementFamilyPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends SimulationManagementFamilyTrait


trait PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}

case class Pdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends PduTrait


case class EntityID(application: Int, entity: Int, site: Int)


case class AcknowledgeReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  acknowledgeFlag: Int,
  requestID: Long,
  responseFlag: Int) extends SimulationManagementWithReliabilityFamilyPduTrait


trait SimulationManagementWithReliabilityFamilyPduTrait extends PduTrait {
  val originatingEntityID: Option[EntityID]
  val receivingEntityID: Option[EntityID]
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class SimulationManagementWithReliabilityFamilyPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends SimulationManagementWithReliabilityFamilyPduTrait


case class AcousticBeamData(fundamentalDataParameters: Option[AcousticBeamFundamentalParameter] = None,
  beamDataLength: Int,
  beamIDNumber: Short,
  pad2: Int)


case class AcousticBeamFundamentalParameter(activeEmissionParameterIndex: Int,
  azimuthalBeamwidth: Float,
  beamCenterAzimuth: Float,
  beamCenterDE: Float,
  deBeamwidth: Float,
  scanPattern: Int)


case class AcousticEmitter(acousticIdNumber: Short, acousticName: Int, function: Short)


case class AcousticEmitterSystem(acousticFunction: Short, acousticID: Short, acousticName: Int)


case class BeamRecordsList(beamRecords: Option[AcousticBeamData]*)


case class AcousticEmitterSystemData(acousticEmitterSystem: Option[AcousticEmitterSystem] = None,
  beamRecordsList: Option[BeamRecordsList] = None,
  emitterLocation: Option[Vector3Float] = None,
  emitterSystemDataLength: Short,
  numberOfBeams: Short,
  pad2: Int)


case class Vector3Float(x: Float, y: Float, z: Float)


case class FixedDatumsList(fixedDatums: Option[FixedDatum]*)


case class VariableDatumsList(variableDatums: Option[VariableDatum]*)


case class ActionRequestPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  actionID: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  requestID: Long) extends SimulationManagementFamilyTrait


case class FixedDatum(fixedDatumID: Long, fixedDatumValue: Long)


case class VariableDatum(variableData: Option[Base64Binary] = None, variableDatumID: Long, variableDatumLength: Long)


case class ActionRequestReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  actionID: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class ActionResponsePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  requestID: Long,
  requestStatus: Long) extends SimulationManagementFamilyTrait


case class ActionResponseReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  requestID: Long,
  responseStatus: Long) extends SimulationManagementWithReliabilityFamilyPduTrait


case class AggregateID(aggregateID: Int, application: Int, site: Int)


case class AggregateMarking(characters: Option[Base64Binary] = None, characterSet: Short)


case class AggregateIDList(aggregateIDList: Option[AggregateID]*)


case class EntityIDListList(entityIDList: Option[EntityID]*)


case class SilentAggregateSystemList(silentAggregateSystemList: Option[EntityType]*)


case class SilentEntitySystemList(silentEntitySystemList: Option[EntityType]*)


case class VariableDatumList(variableDatumList: Option[VariableDatum]*)


case class AggregateStatePdu(aggregateID: Option[EntityID] = None,
  aggregateIDList: Option[AggregateIDList] = None,
  aggregateMarking: Option[AggregateMarking] = None,
  aggregateType: Option[EntityType] = None,
  centerOfMass: Option[Vector3Double] = None,
  dimensions: Option[Vector3Float] = None,
  entityIDListList: Option[EntityIDListList] = None,
  orientation: Option[Orientation] = None,
  silentAggregateSystemList: Option[SilentAggregateSystemList] = None,
  silentEntitySystemList: Option[SilentEntitySystemList] = None,
  variableDatumList: Option[VariableDatumList] = None,
  velocity: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityManagementFamilyPduTrait {
}

trait EntityManagementFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}

case class EntityManagementFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityManagementFamilyPduTrait

case class EntityType(category: Short,
  country: Int,
  domain: Short,
  entityKind: Short,
  extra: Short,
  spec: Short,
  subcategory: Short)


case class Vector3Double(x: Double, y: Double, z: Double)

case class Orientation(phi: Float, psi: Float, theta: Float)

case class AggregateType(aggregateKind: Short,
  category: Short,
  country: Int,
  domain: Short,
  extra: Short,
  specificInfo: Short,
  subcategory: Short)


case class AngularVelocityVector(x: Float, y: Float, z: Float)


case class AntennaLocation(antennaLocation: Option[Vector3Double] = None,
  relativeAntennaLocation: Option[Vector3Float] = None)


case class ApaData(parameterIndex: Int, parameterValue: Short)


case class ObjectLocationList(objectLocation: Option[Vector3Double]*)


case class ArealObjectStatePdu(objectAppearance: Option[SixByteChunk] = None,
  objectID: Option[EntityID] = None,
  objectLocationList: Option[ObjectLocationList] = None,
  objectType: Option[EntityType] = None,
  receivingID: Option[SimulationAddress] = None,
  referencedObjectID: Option[EntityID] = None,
  requesterID: Option[SimulationAddress] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  forceID: Short,
  modifications: Short,
  numberOfPoints: Int,
  updateNumber: Int) extends SyntheticEnvironmentFamilyPduTrait


trait SyntheticEnvironmentFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class SyntheticEnvironmentFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends SyntheticEnvironmentFamilyPduTrait


case class SixByteChunk(otherParameters: Option[Base64Binary] = None)


case class SimulationAddress(application: Int, site: Int)


case class ArticulationParameter(changeIndicator: Short,
  parameterType: Int,
  parameterTypeDesignator: Short,
  parameterValue: Double,
  partAttachedTo: Int)


case class BeamAntennaPattern(beamDirection: Option[Orientation] = None,
  azimuthBeamwidth: Float,
  elevationBeamwidth: Float,
  ex: Float,
  ez: Float,
  padding1: Short,
  padding2: Byte,
  phase: Float,
  referenceSystem: Float)


case class BeamData(beamAzimuthCenter: Float,
  beamAzimuthSweep: Float,
  beamElevationCenter: Float,
  beamElevationSweep: Float,
  beamSweepSync: Float)


case class BurstDescriptor(munition: Option[EntityType] = None,
  fuse: Int,
  quantity: Int,
  rate: Int,
  warhead: Int)


case class ClockTime(hour: Int, timePastHour: Long)


case class CollisionElasticPdu(collidingEntityID: Option[EntityID] = None,
  collisionEventID: Option[EventID] = None,
  contactVelocity: Option[Vector3Float] = None,
  issuingEntityID: Option[EntityID] = None,
  location: Option[Vector3Float] = None,
  unitSurfaceNormal: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityInformationFamilyPduTrait {
}



trait EntityInformationFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class EntityInformationFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityInformationFamilyPduTrait


case class EventID(application: Int, eventNumber: Int, site: Int)


case class CollisionPdu(collidingEntityID: Option[EntityID] = None,
  eventID: Option[EventID] = None,
  issuingEntityID: Option[EntityID] = None,
  location: Option[Vector3Float] = None,
  velocity: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  collisionType: Short,
  mass: Float,
  pad: Byte) extends EntityInformationFamilyPduTrait

case class CommentPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long) extends SimulationManagementFamilyTrait

case class CommentReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long) extends SimulationManagementWithReliabilityFamilyPduTrait


case class CreateEntityPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  requestID: Long) extends SimulationManagementFamilyTrait


case class CreateEntityReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait

case class DataPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  padding1: Long,
  requestID: Long) extends SimulationManagementFamilyTrait

case class DataQueryPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  requestID: Long,
  timeInterval: Long) extends SimulationManagementFamilyTrait

case class DataQueryReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short,
  timeInterval: Long) extends SimulationManagementWithReliabilityFamilyPduTrait

case class DataReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class DeadReckoningParameter(entityAngularVelocity: Option[Vector3Float] = None,
  entityLinearAcceleration: Option[Vector3Float] = None,
  otherParameters: Option[Base64Binary] = None,
  deadReckoningAlgorithm: Short)


case class DesignatorPdu(designatedEntityID: Option[EntityID] = None,
  designatingEntityID: Option[EntityID] = None,
  designatorSpotLocation: Option[Vector3Double] = None,
  designatorSpotWrtDesignated: Option[Vector3Float] = None,
  entityLinearAcceleration: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  codeName: Int,
  deadReckoningAlgorithm: Byte,
  designatorCode: Int,
  designatorPower: Float,
  designatorWavelength: Float,
  padding1: Int,
  padding2: Byte) extends DistributedEmissionsFamilyPduTrait


trait DistributedEmissionsFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class DistributedEmissionsFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends DistributedEmissionsFamilyPduTrait


case class ArticulationParametersList(articulationParameters: Option[ArticulationParameter]*)


case class DetonationPdu(firingEntityID: Option[EntityID] = None,
  targetEntityID: Option[EntityID] = None,
  articulationParametersList: Option[ArticulationParametersList] = None,
  burstDescriptor: Option[BurstDescriptor] = None,
  eventID: Option[EventID] = None,
  locationInEntityCoordinates: Option[Vector3Float] = None,
  locationInWorldCoordinates: Option[Vector3Double] = None,
  munitionID: Option[EntityID] = None,
  velocity: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  detonationResult: Short,
  numberOfArticulationParameters: Short,
  pad: Short) extends WarfareFamilyPduTrait


trait WarfareFamilyPduTrait extends PduTrait {
  val firingEntityID: Option[EntityID]
  val targetEntityID: Option[EntityID]
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class WarfareFamilyPdu(firingEntityID: Option[EntityID] = None,
  targetEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends WarfareFamilyPduTrait


case class EightByteChunk(otherParameters: Option[Base64Binary] = None)


case class TrackJamTargetsList(trackJamTargets: Option[TrackJamTarget]*)


case class ElectronicEmissionBeamData(fundamentalParameterData: Option[FundamentalParameterData] = None,
  trackJamTargetsList: Option[TrackJamTargetsList] = None,
  beamDataLength: Short,
  beamFunction: Short,
  beamIDNumber: Short,
  beamParameterIndex: Int,
  highDensityTrackJam: Short,
  jammingModeSequence: Long,
  numberOfTrackJamTargets: Short,
  pad4: Short)


case class FundamentalParameterData(beamAzimuthCenter: Float,
  beamAzimuthSweep: Float,
  beamElevationCenter: Float,
  beamElevationSweep: Float,
  beamSweepSync: Float,
  effectiveRadiatedPower: Float,
  frequency: Float,
  frequencyRange: Float,
  pulseRepetitionFrequency: Float,
  pulseWidth: Float)


case class TrackJamTarget(trackJam: Option[EntityID] = None, beamID: Short, emitterID: Short)


case class BeamDataRecordsList(beamDataRecords: Option[ElectronicEmissionBeamData]*)


case class ElectronicEmissionSystemData(beamDataRecordsList: Option[BeamDataRecordsList] = None,
  emitterSystem: Option[EmitterSystem] = None,
  location: Option[Vector3Float] = None,
  emissionsPadding2: Int,
  numberOfBeams: Short,
  systemDataLength: Short)


case class EmitterSystem(emitterIdNumber: Short, emitterName: Int, function: Short)


case class SystemsList(systems: Option[ElectronicEmissionSystemData]*)


case class ElectronicEmissionsPdu(emittingEntityID: Option[EntityID] = None,
  eventID: Option[EventID] = None,
  systemsList: Option[SystemsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfSystems: Short,
  paddingForEmissionsPdu: Int,
  stateUpdateIndicator: Short) extends DistributedEmissionsFamilyPduTrait

case class EntityStatePdu(alternativeEntityType: Option[EntityType] = None,
  articulationParametersList: Option[ArticulationParametersList] = None,
  deadReckoningParameters: Option[DeadReckoningParameter] = None,
  entityID: Option[EntityID] = None,
  entityLinearVelocity: Option[Vector3Float] = None,
  entityLocation: Option[Vector3Double] = None,
  entityOrientation: Option[Orientation] = None,
  entityType: Option[EntityType] = None,
  marking: Option[Marking] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  capabilities: Int,
  entityAppearance: Int,
  forceId: Short,
  numberOfArticulationParameters: Byte) extends EntityInformationFamilyPduTrait


case class Marking(characters: Option[Base64Binary] = None, characterSet: Short)

case class EntityStateUpdatePdu(articulationParametersList: Option[ArticulationParametersList] = None,
  entityID: Option[EntityID] = None,
  entityLinearVelocity: Option[Vector3Float] = None,
  entityLocation: Option[Vector3Double] = None,
  entityOrientation: Option[Orientation] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  entityAppearance: Int,
  numberOfArticulationParameters: Short,
  padding1: Byte) extends EntityInformationFamilyPduTrait


case class Environment(environmentType: Long,
  geometry: Short,
  length: Short,
  padding1: Short,
  padding2: Short,
  recordIndex: Short)


case class EnvironmentRecordsList(environmentRecords: Option[Environment]*)


case class EnvironmentalProcessPdu(environmentalProcessID: Option[EntityID] = None,
  environmentRecordsList: Option[EnvironmentRecordsList] = None,
  environmentType: Option[EntityType] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  environmentStatus: Short,
  modelType: Short,
  numberOfEnvironmentRecords: Short,
  sequenceNumber: Int) extends SyntheticEnvironmentFamilyPduTrait


case class EventReportPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  eventType: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  padding1: Long) extends SimulationManagementFamilyTrait

case class EventReportReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  eventType: Int,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  pad1: Long) extends SimulationManagementWithReliabilityFamilyPduTrait


case class FastEntityStatePdu(articulationParametersList: Option[ArticulationParametersList] = None,
  marking: Option[Base64Binary] = None,
  otherParameters: Option[Base64Binary] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityInformationFamilyPduTrait {
}



case class FirePdu(firingEntityID: Option[EntityID] = None,
  targetEntityID: Option[EntityID] = None,
  burstDescriptor: Option[BurstDescriptor] = None,
  eventID: Option[EventID] = None,
  locationInWorldCoordinates: Option[Vector3Double] = None,
  munitionID: Option[EntityID] = None,
  velocity: Option[Vector3Float] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  fireMissionIndex: Int,
  rangeToTarget: Float) extends WarfareFamilyPduTrait


case class FourByteChunk(otherParameters: Option[Base64Binary] = None)


case class FundamentalParameterDataIff(applicableModes: Short,
  burstLength: Long,
  erp: Float,
  frequency: Float,
  pad2: Int,
  pad3: Short,
  pgrf: Float,
  pulseWidth: Float)


trait GridAxisRecordable {
  val dataRepresentation: Int
  val sampleType: Int
}


case class GridAxisRecord(dataRepresentation: Int, sampleType: Int) extends GridAxisRecordable


case class DataValuesOneByteChunkList(dataValues: Option[OneByteChunk]*)


case class GridAxisRecordRepresentation0(dataValuesList: Option[DataValuesOneByteChunkList] = None,
  dataRepresentation: Int,
  sampleType: Int,
  numberOfBytes: Int) extends GridAxisRecordable


case class OneByteChunk(otherParameters: Option[Base64Binary] = None)


case class DataValuesTwoByteChunkList(dataValues: Option[TwoByteChunk]*)


case class GridAxisRecordRepresentationTwoByteChunk(dataValuesList: Option[DataValuesTwoByteChunkList] = None,
  dataRepresentation: Int,
  sampleType: Int,
  fieldOffset: Float,
  fieldScale: Float,
  numberOfValues: Int) extends GridAxisRecordable


case class TwoByteChunk(otherParameters: Option[Base64Binary] = None)


case class DataValuesFourByteChunkList(dataValues: Option[FourByteChunk]*)


case class GridAxisRecordRepresentationFourByteChunk(dataValuesList: Option[DataValuesFourByteChunkList] = None,
  dataRepresentation: Int,
  sampleType: Int,
  numberOfValues: Int) extends GridAxisRecordable


case class GridDataList(gridDataList: Option[GridAxisRecordable]*)


case class GriddedDataPdu(environmentType: Option[EntityType] = None,
  environmentalSimulationApplicationID: Option[EntityID] = None,
  gridDataList: Option[GridDataList] = None,
  orientation: Option[Orientation] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends SyntheticEnvironmentFamilyPduTrait {
}



trait IffAtcNavAidsLayerPduTrait extends DistributedEmissionsFamilyPduTrait {
  val emittingEntityId: Option[EntityID]
  val eventID: Option[EventID]
  val fundamentalParameters: Option[IffFundamentalData]
  val location: Option[Vector3Float]
  val systemID: Option[SystemID]
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
  val pad2: Int
}


case class IffAtcNavAidsLayerPdu(emittingEntityId: Option[EntityID] = None,
  eventID: Option[EventID] = None,
  fundamentalParameters: Option[IffFundamentalData] = None,
  location: Option[Vector3Float] = None,
  systemID: Option[SystemID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  pad2: Int) extends IffAtcNavAidsLayerPduTrait


case class IffFundamentalData(alternateParameter4: Short,
  informationLayers: Short,
  modifier: Short,
  parameter1: Int,
  parameter2: Int,
  parameter3: Int,
  parameter4: Int,
  parameter5: Int,
  parameter6: Int,
  systemStatus: Short)


case class SystemID(changeOptions: Short, systemMode: Short, systemName: Int, systemType: Int)


case class FundamentalIffParametersList(fundamentalIffParameters: Option[FundamentalParameterDataIff]*)


case class IffAtcNavAidsLayer2Pdu(emittingEntityId: Option[EntityID] = None,
  eventID: Option[EventID] = None,
  fundamentalParameters: Option[IffFundamentalData] = None,
  location: Option[Vector3Float] = None,
  systemID: Option[SystemID] = None,
  beamData: Option[BeamData] = None,
  fundamentalIffParametersList: Option[FundamentalIffParametersList] = None,
  layerHeader: Option[LayerHeader] = None,
  secondaryOperationalData: Option[BeamData] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  pad2: Int) extends IffAtcNavAidsLayerPduTrait


case class LayerHeader(layerNumber: Short, layerSpecificInformation: Short, length: Int)


case class IntercomCommunicationsParameters(recordLength: Int, recordSpecificField: Long, recordType: Int)


case class IntercomParametersList(intercomParameters: Option[IntercomCommunicationsParameters]*)


case class IntercomControlPdu(entityId: Option[EntityID] = None,
  intercomParametersList: Option[IntercomParametersList] = None,
  masterEntityID: Option[EntityID] = None,
  sourceEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int) extends RadioCommunicationsFamilyPduTrait {
}



trait RadioCommunicationsFamilyPduTrait extends PduTrait {
  val entityId: Option[EntityID]
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
  val radioId: Int
}


case class RadioCommunicationsFamilyPdu(entityId: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int) extends RadioCommunicationsFamilyPduTrait


case class DataList(data: Option[OneByteChunk]*)


case class IntercomSignalPdu(entityId: Option[EntityID] = None,
  dataList: Option[DataList] = None,
  entityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int,
  communicationsDeviceID: Int,
  dataLength: Int,
  encodingScheme: Int,
  sampleRate: Long,
  samples: Int,
  tdlType: Int) extends RadioCommunicationsFamilyPduTrait


case class GroupedEntityDescriptionsList(groupedEntityDescriptions: Option[VariableDatum]*)


case class IsGroupOfPdu(groupEntityID: Option[EntityID] = None,
  groupedEntityDescriptionsList: Option[GroupedEntityDescriptionsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  groupedEntityCategory: Short,
  latitude: Double,
  longitude: Double,
  numberOfGroupedEntities: Short,
  pad2: Long) extends EntityManagementFamilyPduTrait


case class IsPartOfPdu(namedLocationID: Option[NamedLocation] = None,
  originatingEntityID: Option[EntityID] = None,
  partEntityType: Option[EntityType] = None,
  partLocation: Option[Vector3Float] = None,
  receivingEntityID: Option[EntityID] = None,
  relationship: Option[Relationship] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends EntityManagementFamilyPduTrait


case class NamedLocation(stationName: Int, stationNumber: Int)


case class Relationship(nature: Int, position: Int)


case class LinearSegmentParametersList(linearSegmentParameters: Option[LinearSegmentParameter]*)


case class LinearObjectStatePdu(linearSegmentParametersList: Option[LinearSegmentParametersList] = None,
  objectID: Option[EntityID] = None,
  objectType: Option[ObjectType] = None,
  receivingID: Option[SimulationAddress] = None,
  referencedObjectID: Option[EntityID] = None,
  requesterID: Option[SimulationAddress] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  forceID: Short,
  numberOfSegments: Short,
  updateNumber: Int) extends SyntheticEnvironmentFamilyPduTrait


case class LinearSegmentParameter(location: Option[Vector3Double] = None,
  orientation: Option[Orientation] = None,
  segmentAppearance: Option[SixByteChunk] = None,
  pad1: Long,
  segmentDepth: Int,
  segmentHeight: Int,
  segmentLength: Int,
  segmentNumber: Short,
  segmentWidth: Int)


case class ObjectType(category: Short,
  country: Int,
  domain: Short,
  entityKind: Short,
  subcategory: Short)


trait LogisticsFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class LogisticsFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends LogisticsFamilyPduTrait


case class MineLocationList(mineLocation: Option[Vector3Float]*)


case class SensorTypesList(sensorTypes: Option[TwoByteChunk]*)


case class MinefieldDataPdu(mineLocationList: Option[MineLocationList] = None,
  mineType: Option[EntityType] = None,
  minefieldID: Option[EntityID] = None,
  requestingEntityID: Option[EntityID] = None,
  sensorTypesList: Option[SensorTypesList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends MinefieldFamilyPduTrait {
}



trait MinefieldFamilyPduTrait extends PduTrait {
  val exerciseID: Short
  val padding: Short
  val pduLength: Int
  val pduType: Short
  val protocolFamily: Short
  val protocolVersion: Short
  val timestamp: Long
}


case class MinefieldFamilyPdu(exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends MinefieldFamilyPduTrait


case class RequestedPerimeterPointsList(requestedPerimeterPoints: Option[Point]*)

case class MinefieldQueryPdu(minefieldID: Option[EntityID] = None,
  requestedMineType: Option[EntityType] = None,
  requestedPerimeterPointsList: Option[RequestedPerimeterPointsList] = None,
  requestingEntityID: Option[EntityID] = None,
  sensorTypesList: Option[SensorTypesList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  dataFilter: Long,
  numberOfPerimeterPoints: Short,
  numberOfSensorTypes: Short,
  pad2: Short,
  requestID: Short) extends MinefieldFamilyPduTrait


case class Point(x: Float, y: Float)


case class MissingPduSequenceNumbersList(missingPduSequenceNumbers: Option[EightByteChunk]*)


case class MinefieldResponseNackPdu(minefieldID: Option[EntityID] = None,
  missingPduSequenceNumbersList: Option[MissingPduSequenceNumbersList] = None,
  requestingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfMissingPdus: Short,
  requestID: Short) extends MinefieldFamilyPduTrait


case class MineTypeList(mineType: Option[EntityType]*)


case class PerimeterPointsList(perimeterPoints: Option[Point]*)


case class MinefieldStatePdu(mineTypeList: Option[MineTypeList] = None,
  minefieldID: Option[EntityID] = None,
  minefieldLocation: Option[Vector3Double] = None,
  minefieldOrientation: Option[Orientation] = None,
  minefieldType: Option[EntityType] = None,
  perimeterPointsList: Option[PerimeterPointsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  appearance: Int,
  forceID: Short,
  minefieldSequence: Int,
  numberOfMineTypes: Int,
  numberOfPerimeterPoints: Short,
  protocolMode: Int) extends MinefieldFamilyPduTrait


case class ModulationType(detail: Int,
  major: Int,
  spreadSpectrum: Int,
  system: Int)


case class PduList(pdus: Option[PduTrait]*)


case class PduContainer(pduList: Option[PduList] = None, numberOfPdu: Int)


case class PointObjectStatePdu(objectID: Option[EntityID] = None,
  objectLocation: Option[Vector3Double] = None,
  objectOrientation: Option[Orientation] = None,
  objectType: Option[ObjectType] = None,
  receivingID: Option[SimulationAddress] = None,
  referencedObjectID: Option[EntityID] = None,
  requesterID: Option[SimulationAddress] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  forceID: Short,
  modifications: Short,
  objectAppearance: Double,
  pad2: Long,
  updateNumber: Int) extends SyntheticEnvironmentFamilyPduTrait


case class PropulsionSystemData(engineRpm: Float, powerSetting: Float)


case class RadioEntityType(category: Short,
  country: Int,
  domain: Short,
  entityKind: Short,
  nomenclature: Int,
  nomenclatureVersion: Short)


case class ReceiverPdu(entityId: Option[EntityID] = None,
  transmitterEntityId: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int,
  padding1: Int,
  receivedPoser: Float,
  receiverState: Int,
  transmitterRadioId: Int) extends RadioCommunicationsFamilyPduTrait


case class RecordIDList(recordIDs: Option[FourByteChunk]*)


case class RecordQueryReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  recordIDsList: Option[RecordIDList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  eventType: Int,
  numberOfRecords: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short,
  time: Long) extends SimulationManagementWithReliabilityFamilyPduTrait


case class RecordSet(pad4: Short,
  recordCount: Int,
  recordID: Long,
  recordLength: Int,
  recordSetSerialNumber: Long,
  recordValues: Int)


case class RemoveEntityPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  requestID: Long) extends SimulationManagementFamilyTrait


case class RemoveEntityReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class RepairCompletePdu(receivingEntityID: Option[EntityID] = None,
  repairingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  padding2: Short,
  repair: Int) extends LogisticsFamilyPduTrait


case class RepairResponsePdu(receivingEntityID: Option[EntityID] = None,
  repairingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  padding1: Short,
  padding2: Byte,
  repairResult: Short) extends LogisticsFamilyPduTrait


case class ResupplyCancelPdu(receivingEntityID: Option[EntityID] = None,
  supplyingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long) extends LogisticsFamilyPduTrait


case class SuppliesList(supplies: Option[SupplyQuantity]*)


case class ResupplyOfferPdu(receivingEntityID: Option[EntityID] = None,
  suppliesList: Option[SuppliesList] = None,
  supplyingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfSupplyTypes: Short,
  padding1: Short,
  padding2: Byte) extends LogisticsFamilyPduTrait


case class SupplyQuantity(supplyType: Option[EntityType] = None, quantity: Short)


case class ResupplyReceivedPdu(receivingEntityID: Option[EntityID] = None,
  suppliesList: Option[SuppliesList] = None,
  supplyingEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfSupplyTypes: Short,
  padding1: Short,
  padding2: Byte) extends LogisticsFamilyPduTrait


case class PropulsionSystemDataList(propulsionSystemData: Option[PropulsionSystemData]*)


case class VectoringSystemDataList(vectoringSystemData: Option[VectoringNozzleSystemData]*)


case class SeesPdu(originatingEntityID: Option[EntityID] = None,
  propulsionSystemDataList: Option[PropulsionSystemDataList] = None,
  vectoringSystemDataList: Option[VectoringSystemDataList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  acousticSignatureRepresentationIndex: Int,
  infraredSignatureRepresentationIndex: Int,
  numberOfPropulsionSystems: Int,
  numberOfVectoringNozzleSystems: Int,
  radarCrossSectionSignatureRepresentationIndex: Int) extends DistributedEmissionsFamilyPduTrait


case class VectoringNozzleSystemData(horizontalDeflectionAngle: Float, verticalDeflectionAngle: Float)

case class ServiceRequestPdu(requestingEntityID: Option[EntityID] = None,
  servicingEntityID: Option[EntityID] = None,
  suppliesList: Option[SuppliesList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfSupplyTypes: Short,
  serviceRequestPadding: Short,
  serviceTypeRequested: Short) extends LogisticsFamilyPduTrait

case class SetDataPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumsList: Option[FixedDatumsList] = None,
  variableDatumsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  padding1: Long,
  requestID: Long) extends SimulationManagementFamilyTrait


case class SetDataReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  fixedDatumRecordsList: Option[FixedDatumsList] = None,
  variableDatumRecordsList: Option[VariableDatumsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfFixedDatumRecords: Long,
  numberOfVariableDatumRecords: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class RecordSetsList(recordSets: Option[RecordSet]*)


case class SetRecordReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  recordSetsList: Option[RecordSetsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfRecordSets: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class ShaftRPMs(currentShaftRPMs: Short, orderedShaftRPMs: Short, shaftRPMRateOfChange: Float)

case class SignalPdu(entityId: Option[EntityID] = None,
  dataList: Option[DataList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int,
  dataLength: Short,
  encodingScheme: Int,
  sampleRate: Long,
  samples: Short,
  tdlType: Int) extends RadioCommunicationsFamilyPduTrait


case class SphericalHarmonicAntennaPattern(harmonicOrder: Byte)


case class StartResumePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  realWorldTime: Option[ClockTime] = None,
  simulationTime: Option[ClockTime] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  requestID: Long) extends SimulationManagementFamilyTrait


case class StartResumeReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  realWorldTime: Option[ClockTime] = None,
  simulationTime: Option[ClockTime] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  pad1: Int,
  pad2: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait


case class StopFreezePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  realWorldTime: Option[ClockTime] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  frozenBehavior: Short,
  padding1: Short,
  reason: Short,
  requestID: Long) extends SimulationManagementFamilyTrait


case class StopFreezeReliablePdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  realWorldTime: Option[ClockTime] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  frozenBehavior: Short,
  pad1: Short,
  reason: Short,
  requestID: Long,
  requiredReliabilityService: Short) extends SimulationManagementWithReliabilityFamilyPduTrait

case class TransferControlRequestPdu(originatingEntityID: Option[EntityID] = None,
  receivingEntityID: Option[EntityID] = None,
  recordSetsList: Option[RecordSetsList] = None,
  transferEntityID: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfRecordSets: Short,
  requestID: Long,
  requiredReliabilityService: Short,
  transferType: Short) extends EntityManagementFamilyPduTrait


case class AntennaPatternList(antennaPatternList: Option[Vector3Float]*)


case class ModulationParametersList(modulationParametersList: Option[Vector3Float]*)


case class TransmitterPdu(entityId: Option[EntityID] = None,
  antennaLocation: Option[Vector3Double] = None,
  antennaPatternListList: Option[AntennaPatternList] = None,
  modulationParametersListList: Option[ModulationParametersList] = None,
  modulationType: Option[ModulationType] = None,
  radioEntityType: Option[RadioEntityType] = None,
  relativeAntennaLocation: Option[Vector3Float] = None,
  transmitterEntityId: Option[EntityID] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  radioId: Int,
  padding1: Int,
  receivedPoser: Float,
  receiverState: Int,
  transmitterRadioId: Int) extends RadioCommunicationsFamilyPduTrait {
}



case class ApaDataList(apaData: Option[ApaData]*)


case class EmitterSystemsList(emitterSystems: Option[AcousticEmitterSystemData]*)


case class ShaftRPMsList(shaftRPMs: Option[ShaftRPMs]*)


case class UaPdu(apaDataList: Option[ApaDataList] = None,
  emitterSystemsList: Option[EmitterSystemsList] = None,
  emittingEntityID: Option[EntityID] = None,
  eventID: Option[EventID] = None,
  shaftRPMsList: Option[ShaftRPMsList] = None,
  exerciseID: Short,
  padding: Short,
  pduLength: Int,
  pduType: Short,
  protocolFamily: Short,
  protocolVersion: Short,
  timestamp: Long,
  numberOfAPAs: Short,
  numberOfShafts: Short,
  numberOfUAEmitterSystems: Short,
  pad: Byte,
  passiveParameterIndex: Int,
  propulsionPlantConfiguration: Short,
  stateChangeIndicator: Byte) extends DistributedEmissionsFamilyPduTrait

