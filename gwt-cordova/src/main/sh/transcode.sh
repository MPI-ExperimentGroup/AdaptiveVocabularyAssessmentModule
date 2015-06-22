#    Created on : June 22, 2015 16:06 PM
#    Author     : Peter Withers <peter.withers@mpi.nl>
#    This bash script is intended to handle audio file transcoding for HTML5 needs.

for filePath in ../resources/static/*.wav ; do 
    echo $filePath
    echo "${filePath%.*}".flac
    ffmpeg -i "$filePath" -acodec libmp3lame "${filePath%.*}".mp3 
    sleep 60
    ffmpeg -i "$filePath" -acodec libvorbis "${filePath%.*}".ogg 
    sleep 60 
done
