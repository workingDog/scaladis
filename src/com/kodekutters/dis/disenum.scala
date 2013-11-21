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


/**
 * Machine Readable Enumeration and Bit Encoded Values Schema, Draft $Revision: 34 $
 *
 */


trait GenericTableTrait {
  val id: BigInt
  val cname: String
  val length: BigInt
  val name: String
  val source: String
}


case class GenericTable(id: BigInt,
                        cname: String,
                        length: BigInt,
                        name: String,
                        source: String) extends GenericTableTrait


trait GenericEntryTrait {
  val description: Option[String]
  val footnote: Option[String]
  val xref: Option[String]
  val deleted: Option[Boolean]
  val unused: Option[Boolean]
}


case class GenericEntry(description: Option[String] = None,
                        footnote: Option[String] = None,
                        xref: Option[String] = None,
                        deleted: Option[Boolean] = None,
                        unused: Option[Boolean] = None) extends GenericEntryTrait


case class Ebv(revisions: Revisions,
               acronyms: Acronyms,
               ebvOption: Seq[EbvOption] = Nil,
               title: String,
               release: String,
               date: javax.xml.datatype.XMLGregorianCalendar,
               href: String,
               organisation: String)

trait EbvOption

case class Revisions(revision: Revision*)


case class Revision(title: String,
                    date: Option[javax.xml.datatype.XMLGregorianCalendar] = None,
                    cr: Option[BigInt] = None,
                    cr2: Option[BigInt] = None)


case class Acronyms(acronym: Acronym*)


case class Acronym(id: String, description: String)


case class Enum(header: Seq[Header] = Nil,
                enumRow: Seq[EnumRow] = Nil,
                id: BigInt,
                cname: String,
                length: BigInt,
                name: String,
                source: String) extends GenericTableTrait with EbvOption


case class Header(col: Col*)


case class Col(id: String, name: String)


case class EnumRow(meta: Seq[Meta] = Nil,
                   description: Option[String] = None,
                   footnote: Option[String] = None,
                   xref: Option[String] = None,
                   deleted: Option[Boolean] = None,
                   unused: Option[Boolean] = None,
                   id: Int,
                   id2: Option[Int] = None) extends GenericEntryTrait


case class Meta(id: String, valueAttribute: String)


case class Bitmask(bitmaskrow: Seq[BitmaskRow] = Nil,
                   id: BigInt,
                   cname: String,
                   length: BigInt,
                   name: String,
                   source: String) extends GenericTableTrait with EbvOption


case class BitmaskRow(enumrow: Seq[EnumRow] = Nil,
                      description: Option[String] = None,
                      footnote: Option[String] = None,
                      xref: Option[String] = None,
                      deleted: Option[Boolean] = None,
                      unused: Option[Boolean] = None,
                      id: BigInt,
                      id2: Option[BigInt] = None,
                      name: Option[String] = None) extends GenericEntryTrait


case class Cet(entity: Seq[Entity] = Nil,
               id: BigInt,
               cname: String,
               length: BigInt,
               name: String,
               source: String) extends GenericTableTrait with EbvOption


case class Entity(category: Seq[Category] = Nil,
                  kind: BigInt,
                  domain: BigInt,
                  country: BigInt,
                  description: Option[String] = None,
                  unused: Option[Boolean] = None,
                  footnote: Option[String] = None,
                  xref: Option[String] = None)


case class Category(subcategory: Seq[Subcategory] = Nil,
                    description: Option[String] = None,
                    footnote: Option[String] = None,
                    xref: Option[String] = None,
                    deleted: Option[Boolean] = None,
                    unused: Option[Boolean] = None,
                    id: BigInt) extends GenericEntryTrait


case class Subcategory(specific: Seq[Specific] = Nil,
                       description: Option[String] = None,
                       footnote: Option[String] = None,
                       xref: Option[String] = None,
                       deleted: Option[Boolean] = None,
                       unused: Option[Boolean] = None,
                       id: BigInt,
                       id2: Option[BigInt] = None) extends GenericEntryTrait


case class Specific(extra: Seq[Extra] = Nil,
                    description: Option[String] = None,
                    footnote: Option[String] = None,
                    xref: Option[String] = None,
                    deleted: Option[Boolean] = None,
                    unused: Option[Boolean] = None,
                    id: BigInt,
                    id2: Option[BigInt] = None) extends GenericEntryTrait


case class Extra(description: Option[String] = None,
                 footnote: Option[String] = None,
                 xref: Option[String] = None,
                 deleted: Option[Boolean] = None,
                 unused: Option[Boolean] = None,
                 id: BigInt) extends GenericEntryTrait


case class Cot(objectValue: Seq[EnumObject] = Nil,
               id: BigInt,
               cname: String,
               length: BigInt,
               name: String,
               source: String) extends GenericTableTrait with EbvOption


case class EnumObject(category: Seq[Category] = Nil,
                      domain: BigInt,
                      kind: BigInt,
                      name: String)


case class Record(field: Seq[EnumField] = Nil,
                  id: BigInt,
                  cname: String,
                  length: BigInt,
                  name: String,
                  source: String) extends GenericTableTrait with EbvOption


case class EnumField(datatype: Seq[DataType] = Nil, name: String)


case class DataType(name: Option[String] = None, typeValue: String)

