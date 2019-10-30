.text 0x00400000
.globl main
main:
li $v0, 5
syscall
mult $v0, $v0
mflo $a0
li $v0, 1
syscall
li $v0, 10
syscall