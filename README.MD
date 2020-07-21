
# TipAndSplit

TipAndSplit is an Android app which calculates the **total bill amount, tip amount, and amount per person**(if the bill is split) for a given **bill amount and tip%**.

## Setup
##### Git clone
Clone the project : `https://git.cs.dal.ca/mpanchal/mc-assignment-1.git`
##### OR
##### Export
Export the .zip file(if provided) in any Android IDE and build the project using gradle.


## Compatibility

TipAndSplit is compatible from Android Lolipop [1] to Android Pie [2] version.

## Currency support

TipAndSplit is currently supporting $CAD but can be easily extend to other currencies. It is achieved by eliminating hardcoding in the code and instead  predefined currency constants.

## Design

TipAndSplit design is minimalistic, intuitive [7], and follows 3-click design [3] to keep the interaction simplistic.

- It uses various event listeners to capture the billing information, computes the result in real time and display it under the bill summary section. User does not have to press any button to see the computed result.
- There is only one button to reset the input fields to default values.
- Alerts the user based on action using Android's Toast library [6].

##### Wireframes

![Tip calculation](/project_resources/wireframes/Calculate_tip.png)
> Tip calculation using bill amount and tip%
http://bit.ly/3cIBgXt


![Tip calculation with bill split](/project_resources/wireframes/Calculate_tip_with_split.png)
> Tip calculation using bill amount, tip%, and split
http://bit.ly/2TS5Oxo

## Architecture

TipAndSplit is based on Google's recommended single-activity architecture [4], supporting separation of concerns, modularity and loose coupling.

- Main activity binds two fragments:  **BillDetailsFragment** and **BillSummaryFragment**
- Implements **SharedViewModel** [5] which helps in inter fragment communication and data sharing.



## Sequence Diagram

High level component interaction.

![Sequence Diagram](/project_resources/TipAndSplitSequenceDiagram.png)

## Workflow

![Sequence Diagram](/project_resources/TipAndSplitWorkflow.gif)
External link: http://bit.ly/3cG7Zg5


## References
[1] https://en.wikipedia.org/wiki/Android_version_history#Android_5.0_Lollipop_(API_21)
[2] https://en.wikipedia.org/wiki/Android_version_history#Android_9_Pie_(API_28)
[3] https://www.nngroup.com/articles/3-click-rule/
[4] https://developer.android.com/jetpack/docs/guide
[5] https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing
[6] https://developer.android.com/guide/topics/ui/notifiers/toasts
[7] https://material.io/components/text-fields/#anatomy