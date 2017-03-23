//
//  ViewController.swift
//  Hollywood
//
//  Created by David Hardy on 23/03/17.
//  Copyright © 2017 Endran. All rights reserved.
//

import UIKit
import Firebase

class ViewController: UIViewController {

    @IBOutlet weak var eventsLabel: UILabel!

    @IBOutlet weak var appsLabel: UILabel!
    
    @IBOutlet weak var usersLabel: UILabel!
    
    @IBOutlet weak var radio1Label: UILabel!
    
    @IBOutlet weak var radio2Label: UILabel!
    
    @IBOutlet weak var radio3Label: UILabel!
    
    @IBOutlet weak var checkedTrueLabel: UILabel!
    
    @IBOutlet weak var checkedFalseLabel: UILabel!
    
    @IBOutlet weak var averageLabel: UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let ref = FIRDatabase.database().reference()
        ref.child("views")
            .observe(FIRDataEventType.value, with: { (snapshot) in
                let e = FireView(snapshot: snapshot)
                DispatchQueue.main.async(execute: {
                    self.eventsLabel.text = "\(e.eventCount)"
                    self.appsLabel.text = "\(e.appId)"
                    self.usersLabel.text = "\(e.users)"
                    self.radio1Label.text = "\(e.radio["r1"]!)"
                    self.radio2Label.text = "\(e.radio["r2"]!)"
                    self.radio3Label.text = "\(e.radio["r3"]!)"
                    self.checkedTrueLabel.text = "\(e.checked["true"]!)"
                    self.checkedFalseLabel.text = "\(e.checked["false"]!)"
                    self.averageLabel.text = "\(e.sliderAverage)"
                })
        });
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

