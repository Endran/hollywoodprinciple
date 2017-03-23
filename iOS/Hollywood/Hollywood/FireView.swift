//
//  FireView.swift
//  Hollywood
//
//  Created by David Hardy on 23/03/17.
//  Copyright © 2017 Endran. All rights reserved.
//

import Foundation

class FireView : FIRDataObject {
    var appId: Int = 0
    var checked: [String: Int] = [:]
    var clientType: [String: Int]? = [:]
    var eventCount: Int = 0
    var radio: [String: Int] = [:]
    var sliderAverage: Double = 0.0
    var users: Int = 0
}

