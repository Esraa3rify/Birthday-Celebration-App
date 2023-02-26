package com.example.remindname.camera


class StatusTracker( val onBlink : () -> Unit) {
    private val statusSequence = mutableListOf<EyeState>()
    private var level = 0
    init {
        reset()
    }
    fun reset(){
        statusSequence.add(EyeState.OPEN)
        statusSequence.add(EyeState.CLOSED)
        statusSequence.add(EyeState.OPEN)
        level = 0
    }

    fun onStatusChange(eyeState: EyeState){
        when(level){
            0->{
                if(eyeState == EyeState.OPEN) {
                    statusSequence.removeAt(0)
                    level+=1
                }
            }
            1->{
                if(eyeState == EyeState.CLOSED){
                    statusSequence.removeAt(0)
                    level+=1
                }else if(eyeState != EyeState.OPEN){
                    reset()
                }
            }
            2 -> {
                if(eyeState == EyeState.OPEN){
                    statusSequence.removeAt(0)
                    level+=1
                }else if(eyeState != EyeState.CLOSED ){
                    reset()
                }
            }
            3->{
                if(statusSequence.isEmpty()){
                    onBlink()
                    level+=1
                }
            }
        }
    }

}