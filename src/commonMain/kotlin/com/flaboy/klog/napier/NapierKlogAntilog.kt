package com.flaboy.klog.napier

import com.flaboy.klog.PlatformLogger
import com.flaboy.klog.RingLogger
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel

/**
 * Napier Antilog implementation that stores logs to klog RingLogger.
 * 
 * Usage:
 * ```
 * val ringLogger = RingLogger(path, config)
 * val platformLogger = MyPlatformLogger()
 * val antilog = NapierKlogAntilog(ringLogger, platformLogger)
 * Napier.base(antilog)
 * ```
 */
class NapierKlogAntilog(
    private val ringLogger: RingLogger,
    private val platformLogger: PlatformLogger
) : Antilog() {
    
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        val finalMessage = message ?: ""
        val finalTag = tag ?: "Napier"
        
        // 1. Output to console using klog PlatformLogger
        when (priority) {
            LogLevel.VERBOSE,
            LogLevel.DEBUG,
            LogLevel.INFO -> {
                platformLogger.log(finalTag, finalMessage)
            }
            LogLevel.WARNING -> {
                platformLogger.logW(finalTag, finalMessage)
            }
            LogLevel.ERROR,
            LogLevel.ASSERT -> {
                platformLogger.logE(finalTag, finalMessage, throwable)
            }
        }
        
        // 2. Store to file (message is already formatted by Napier)
        val level = when (priority) {
            LogLevel.VERBOSE,
            LogLevel.DEBUG,
            LogLevel.INFO -> 1.toByte()
            LogLevel.WARNING -> 2.toByte()
            LogLevel.ERROR,
            LogLevel.ASSERT -> 3.toByte()
        }
        ringLogger.append(finalMessage, level)
    }
}

