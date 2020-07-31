	.text 0x00400000
	.globl main
main:
	la $a0, prompt
	li $v0, 4
	syscall
	
	li $v0, 5
	syscall
	
	li $t0, 2
	div $v0, $t0
	mfhi $t0
	
	beq $t0, $zero, if
	j else
if:
	la $a0, even
	j end

else:
	la $a0, odd
	j end
	
end:
	li $v0, 4
	syscall
	li $v0, 10
	syscall

	.data
even:
	.asciiz "The number is even!!\n"
odd:
	.asciiz "The number is odd!!\n"
prompt:
	.asciiz "Enter a number to check its parity!\n"