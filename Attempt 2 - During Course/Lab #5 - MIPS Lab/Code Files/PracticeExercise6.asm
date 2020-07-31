	.data
x:
.word 0
y:
.word 0
nl:
.asciiz "\n"

	.text
	.globl main

main:
	li $t0, 2
	sw $t0, x

	lw $t0, x
	addu $t0, $t0, 1
	sw $t0, y

	lw $t0, x
	lw $t1, y
	addu $t0, $t0, $t1
	sw $t0, x

	lw $t0, x
	lw $t1, y
	ble $t0, $t1, skipIf1
	li $v0, 1
	move $a0, $t1
	syscall
li $v0, 4
la $a0, nl
syscall

skipIf1:
startLoop:
	lw $t0, x
	bge $t0, 10, endLoop

	lw $t0, x
	li $v0, 1
	move $a0, $t0
	syscall
	li $v0, 4
la $a0, nl
syscall

	lw $t0, x
	addu $t0, $t0, 1
	sw $t0, x

	j startLoop

endLoop:
	li $v0, 10
	syscall
