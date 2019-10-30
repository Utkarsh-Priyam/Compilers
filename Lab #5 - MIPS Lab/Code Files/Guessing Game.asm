.text 0x00400000
.globl main
main:
li $t0, 50
li $t2, 0
guess:
li $v0, 5
syscall
move $t1, $v0
beq $t0, $t1, end
addu $t2, $t2, 1
blt $t0, $t1, h
l:
la $a0, low
li $v0, 4
syscall
j loopEnd
h:
la $a0, high
li $v0, 4
syscall
loopEnd:
j guess
end:
la $a0, win1
li $v0, 4
syscall
move $a0, $t2
li $v0, 1
syscall
la $a0, win2
li $v0, 4
syscall
li $v0, 10
syscall
.data
nl:
.asciiz "\n"
win1:
.asciiz "You win! You guessed the correct number!\nIt took you "
win2:
.asciiz " guesses to guess my number!\n"
low:
.asciiz "The number you guessed is too low.\n"
high:
.asciiz "The number you guessed is too high.\n"