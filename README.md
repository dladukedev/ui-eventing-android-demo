# UI Eventing Android Demo

### Overview
A Demo app built to show off an assortment of options for handling UI or One Time Events on Android (e.g. showing a snackbar or prompting navigation)

Demonstrated Methods
- [Callbacks](#Callbacks)
- [Mark on Send](#Mark-on-Send)
- [Fire and Forget](#Fire-and-Forget)
- [Mark on Consume](#Mark-on-Consume)
- [Events as State](#Events-as-State)

## Callbacks
- Passing an implementation of a Callback Interface from the View to the ViewModel
- **Considerations**
  - Tight Coupling
  - Requires Full Implementation
  - No Guarantees of Call
  - Need to Ensure Life-Cycle Aware

## Mark on Send
- Once the Event has been sent, the ViewModel records that the event has been sent without View confirmation
- **Considerations**
  - Loose Coupling
  - No Guarantee of Consumption
  - Flexibility in Determining Sent 
  - Verbose

## Fire and Forget
- The ViewModel sends the event immediately upon creation, neither the View or ViewModel tracks the event
- **Considerations**
  - Loose Coupling
  - No Guarantee of Consumption
  - No Flexibility Determining Sent
  - Low Effort to Implement

## Mark on Consume
- The ViewModel maintains the sent event until the View notifies the ViewModel that the event has been handled
- **Considerations**
  - Loose Coupling
  - Guarantee of Consumption
  - Flexibility Determining Sent
  - Verbose


## Events as State
- The ViewModel doesn't maintain the idea of events, but instead treats everything as state, the View calls the ViewModel to notify it if state needs changed
- **Considerations**
  - Loose Coupling
  - Highly Reusable
  - Guarantee of Consumption
  - Verbose



### Other Resources
- [Android Developers - UI events](https://developer.android.com/topic/architecture/ui-layer/events)
- [Android SingleLiveEvent Redux with Kotlin Flow](https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055)
- [ViewModel: One-off event antipatterns](https://medium.com/androiddevelopers/viewmodel-one-off-event-antipatterns-16a1da869b95)
- [Sending ViewModel Events to the UI in Android](https://proandroiddev.com/sending-view-model-events-to-the-ui-eef76bdd632c)

