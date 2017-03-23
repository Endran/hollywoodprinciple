//
//  FireView.swift
//  Hollywood
//
//  Created by David Hardy on 23/03/17.
//  Copyright © 2017 Endran. All rights reserved.
//

import Foundation
import Firebase

struct Event {
    var appId: String = ""
    var name: String = ""
    var radioId: Int = 0
    var checked: Bool = false
    var slider: Int = 0
    var clientType: String = "iOS"
    var timeStamp: Any = FIRServerValue.timestamp()
}
