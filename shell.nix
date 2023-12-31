{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  packages = with pkgs; [
    scala_3
    git
    p7zip
    ffmpeg
  ];
}
