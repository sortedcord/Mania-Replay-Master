package me.replaymaster.model

import me.replaymaster.debug
import me.replaymaster.logLine
import org.yaml.snakeyaml.Yaml
import java.io.File

data class Config(
        var speed: Int = 15,
        var width: Int = 540,
        var height: Int = 960,
        var stroke: Int = 3,
        var actionHeight: Int = 7,
        var blockHeight: Int = 40,
        var framePerSecond: Int = 60,
        var isMalodyPE: Boolean = true,
        var judgementColor: List<String> = listOf(
                "FFFFFF", // max, best
                "FFD237", // 300, cool
                "79D020", // 200, good
                "1E68C5", // 100
                "E1349B" // 50
        ),
        var missColor: String = "FF0000",
        var longNoteColor: String = "646464",
        var debug: Boolean = false,
        var codec: String = "libx264",
        var outputDir: String = "out",
        var isServer: Boolean = false,
        var maxStepSize: Int = 3000,
        var ffmpegMaxProcessingSize: Int = 20,
        var exportJudgementResults: Boolean = false
) {

    companion object {
        var INSTANCE: Config = Config()

        fun init(yamlPath: File) {
            if (yamlPath.exists()) {
                debug("Config exists!")
                refresh(yamlPath)
            } else {
                yamlPath.writeText(defaultSetting)
            }
            logLine("config.hint", yamlPath.absolutePath)
            println()
        }

        fun refresh(yamlPath: File) {
            if (yamlPath.exists()) {
                INSTANCE = Yaml().load("!!me.replaymaster.model.Config\n" + yamlPath.readText())
            }
        }

        val defaultSetting = """
            # Falling down speed, in pixel / frame
            speed: 15
            
            # FPS: frame / second
            framePerSecond: 60
            
            # Only valid when using Malody. true: PE, false: PC
            malodyPE: true
            
            # UI settings
            width: 540
            height: 960
            actionHeight: 7 # hit bar height
            blockHeight: 40 # note height
            stroke: 3
            
            # Judgement colors
            judgementColor:
              - FFFFFF
              - FFD237
              - 79D020
              - 1E68C5
              - E1349B
            longNoteColor: '646464'
            missColor: FF0000
            
            # Advanced
            outputDir: 'out'
            exportJudgementResults: false
            codec: libx264
            debug: false
        """.trimIndent()
    }
}
