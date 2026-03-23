package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs;

    public Playlist(String name){
        this.name = name;
        this.songs = new Song[0];
    }

    public String getName() {return name; }

    public void addSong(Song song){
        songs = Arrays.copyOf(this.songs, songs.length + 1);
        songs[songs.length - 1] = song;

        System.out.println("Am adaugat " + song);
    }

    public void printSortedByTitle(){
        Song[] copy = Arrays.copyOf(this.songs, songs.length);
        Arrays.sort(copy);

        for(Song s : copy){
            System.out.println("Piesa: " + s);
        }
    }

    public void printSortedByDuration(){
        Song[] copy = Arrays.copyOf(this.songs, songs.length);
        Arrays.sort(copy, new SongDurationComparator());

        for(Song s : copy){
            System.out.println("Piesa: " + s);
        }
    }

    public int getTotalDuration() {
        int suma = 0;
        for (Song s : songs){
            suma += s.durationSeconds();
        }
        return suma;
    }
}