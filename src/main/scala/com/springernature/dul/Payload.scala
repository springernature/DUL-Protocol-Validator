package com.springernature.dul

import java.net.URL
import java.util.UUID

case class Payload(uuid: UUID,
                   messageType: MessageType,
                   callback: URL,
                   message: Message)
