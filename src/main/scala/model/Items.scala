package model

case class ItemOrder(description: String)

case class ItemCreated(reference: Long)

case class Item(reference: Long, description: String)
