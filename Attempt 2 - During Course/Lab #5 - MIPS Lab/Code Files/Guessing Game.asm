	.text 0x00400000
	.globl main
main:
	li $v0, 42
	li $a1, 200
	syscall
	move $t0, $a0
	li $t2, 0

guess:
	li $v0, 5
	syscall
	move $t1, $v0
	addu $t2, $t2, 1
	beq $t0, $t1, endLoop
	blt $t0, $t1, high
low:
	la $a0, lowMsg
	li $v0, 4
	syscall
	j endIf
high:
	la $a0, highMsg
	li $v0, 4
	syscall

endIf:
	j guess

endLoop:
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
lowMsg:
	.asciiz "The number you guessed is too low.\n"
highMsg:
	.asciiz "The number you guessed is too high.\n"
