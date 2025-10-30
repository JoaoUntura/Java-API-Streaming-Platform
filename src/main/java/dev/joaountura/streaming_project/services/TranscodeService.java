package dev.joaountura.streaming_project.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TranscodeService {
    private static final Path UPLOAD_DIR = Paths.get("videos");
    public static final Path OUTPUT_SEGMENTS_DIR = Paths.get("segments");
    public static final Path OUTPUT_MANIFEST_DIR = Paths.get("manifests");

    public void transcodeVideo(String fileId) throws IOException, InterruptedException {

        if (!Files.exists(OUTPUT_SEGMENTS_DIR)) Files.createDirectories(OUTPUT_SEGMENTS_DIR);
        if (!Files.exists(OUTPUT_MANIFEST_DIR)) Files.createDirectories(OUTPUT_MANIFEST_DIR);

        Path videoPath = UPLOAD_DIR.resolve(fileId);

        Path segmentsDirectory = OUTPUT_SEGMENTS_DIR.resolve(fileId);
        Files.createDirectories(segmentsDirectory);
        String segments = segmentsDirectory.resolve("segments_%03d.ts").toString();

        Path masterDirectory = OUTPUT_MANIFEST_DIR.resolve(fileId);
        Files.createDirectories(masterDirectory);
        String master = masterDirectory.resolve("master.m3u8").toString();

        ProcessBuilder videoTranscoder = new ProcessBuilder(
                "ffmpeg", "-i", videoPath.toString(),
                "-c:v", "libx264", "-c:a", "aac",
                "-b:v", "4000K",
                "-hls_time", "6",
                "-hls_playlist_type", "vod",
                "-hls_segment_filename", segments ,
                master

        );

        videoTranscoder.inheritIO();
        Process process = videoTranscoder.start();

        int exitCode = process.waitFor();
        if (exitCode != 0) throw new RuntimeException("FFmpeg error " + exitCode);

        manifestUpdate(masterDirectory.resolve("master.m3u8"), fileId);

    }

    private void manifestUpdate(Path masterDestination, String fileId) throws IOException {

        List<String> lines = Files.readAllLines(masterDestination);

       List<String> updatedManifest = lines.stream().map(line -> (
            line.endsWith(".ts") ? "/" + fileId + "/" + line : line
        )).toList();

        Files.write(masterDestination, updatedManifest);

    }

}
