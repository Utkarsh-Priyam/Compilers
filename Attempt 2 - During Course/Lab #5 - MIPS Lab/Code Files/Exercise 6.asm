	.text 0x00400000
	.globl main
main:
	la $a0, prompt
	li $v0, 4
	syscall

	li $v0, 5
	syscall
	move $t0, $v0
	li $v0, 5
	syscall
	move $t1, $v0
	li $v0, 5
	syscall
	move $t2, $v0
	
	move $a1, $t0
	
startLoop:
	bge $a1, $t1, endLoop
	
	move $a0, $a1
	li $v0, 1
	syscall
	
	la $a0, nl
	li $v0, 4
	syscall
	
	addu $a1, $a1, $t2
	
	j startLoop

endLoop:
	li $v0, 10
	syscall

	.data
nl:
	.asciiz "\n"
prompt:
	.asciiz "Enter a lower bound, upper bound, and step size, respectively. Enjoy!\n"
